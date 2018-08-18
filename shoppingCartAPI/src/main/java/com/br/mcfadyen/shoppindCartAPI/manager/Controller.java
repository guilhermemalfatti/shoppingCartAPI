package com.br.mcfadyen.shoppindCartAPI.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.br.mcfadyen.shoppindCartAPI.model.CommerceItem;
import com.br.mcfadyen.shoppindCartAPI.model.Product;
import com.br.mcfadyen.shoppindCartAPI.model.ShopingCart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
/**
 * Class that have controller method os the application
 */
public class Controller {
	//the list of mock product
	private static List<Product> products = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger(Controller.class);
	private static final String SHOPPING_CART = "shopingCart";
	private static final String PRODUCT_ID = "product_id";
	private static final String QUANTITY = "quantity";
	
	/**
	 * Method to create a new mock product
	 * 
	 * @param id The id of product
	 * @param name The name of product
	 * @param image The image url of product
	 * @param price The product price
	 */
	private static void createProduct(String id, String name, String image, BigDecimal price){
		Product product = new Product(id, name, image, price);		

		if(product != null && products != null){
			products.add(product);
		}else{
			logger.error("Error to create a new product");
		}		
	}

	/**
	 * Method to setup initial configurations, so far just creating mock products (not in DB)
	 */
	public static void appSetup(){
		logger.info("Create products begin");

		createProduct("01", "TÊNIS COM AMARRAÇÃO BASIC", "http://imagens.passarela.com.br/passarelaEstatico/imagens/produto/7020848112/7020848112_1_G.JPG", new BigDecimal("59.97"));
		createProduct("02", "TÊNIS FEMININO DE LUREX", "https://lojaspompeia.vteximg.com.br/arquivos/ids/390677-1000-1000/lojas-pompeia-sapato-bebece-salto-7.5-borgonha5.jpg?v=636129060832900000", new BigDecimal("53.30"));
		createProduct("03", "TÊNIS PÉROLAS SEM CADARÇO", "https://www.superepi.com.br/fotos/zoom/801fz1/sapato-de-seguranca-de-amarrar-fujiwara-sns-ca-27840.jpg", new BigDecimal("53.30"));
		createProduct("04", "Mesa de Jantar Gravetto 108 cm", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLmBiOy-xn5OEPnIEk2BquzcXVonbUrKSdqTXs8I1ZAfCd87CT", new BigDecimal("153.30"));
		createProduct("05", "Mesa Extensível Juriti - Amarela", "https://www.oppa.com.br/fotos-moveis/m/e/mesa_extensivel_juriti_amarela_1.jpg", new BigDecimal("223.21"));
		createProduct("06", "Mesa de Centro Recanto", "https://cdn-fotos-s3.meumoveldemadeira.com.br/fotos-moveis/foto-completa-do-mesa-de-centro-recanto_album.jpg", new BigDecimal("111.30"));
		createProduct("07", "Bicicleta Wendy Bike Aro 26 21", "https://i.zst.com.br/thumbs/45/12/38/45800716.jpg", new BigDecimal("1000.90"));
		createProduct("08", "Bicicleta Aro 29 | Pedalokos Bike", "https://media.dooca.com.br/pedalokos.com.br/produto/grande/bicicleta-29-bmc-speedfox-02-carbon-130mm-deore-xt-20v-m-15217475463891.png", new BigDecimal("5500.30"));
		createProduct("09", "Bicicleta Infantil Treme Terra", "https://img.tremeterra.com.br/imagens/produtos/0000001220/0/Ampliada/bicicleta-infantil-treme-terra-rhino-aro-14.jpg", new BigDecimal("333.30"));
		
		logger.info("Create products end");
	}

	/**
	 * @return products The list of products
	 */
	public static List<Product> getProducts() {
		logger.info("List products begin");
		return products;
	}

	/**
	 * Method responsible to get the current shopping cart for the session
	 * 
	 * @param req The current request
	 * 
	 * @return The current ShopingCart
	 */
	public static ShopingCart getShoppingCart(Request req){
		ShopingCart cart = req.session().attribute(SHOPPING_CART);
		logger.info("Session: " + req.session().id() );

		if(cart == null){
			logger.info("cart do not exist in this session.");
			
			cart = new ShopingCart();
			req.session().attribute(SHOPPING_CART, cart);
		}
		return cart;
	}

	private static Product getProduct(String id){
		Product product = null;
		if(products != null){
			product = products.stream().filter(p -> id.equals(p.getId())).findAny().orElse(null);
		}	

		return product;
	}

	public static CommerceItem addOrUpdateItem(Request req, Response resp){
		Integer quantity = Integer.valueOf(req.queryParams(QUANTITY).equals("") ? "0" : req.queryParams(QUANTITY));
		String prodId = req.queryParams(PRODUCT_ID);
		CommerceItem item = null;
		ShopingCart cart = getShoppingCart(req);

		if(prodId != null && quantity != null && quantity > 0){
			
			if(cart.getItems() != null && cart.getItems().size() > 0){
				//check whether the item exist in the cart
				item = cart.getItems().stream().filter(p -> prodId.equals(p.getProduct_id())).findAny().orElse(null);
			}

			if(item != null){				
				item = updateItem(quantity, item);
			}else{				
				item = addItem(quantity, prodId, cart, resp);	
			}
			cart.calcAmount();
			return item;
		}else{
			logger.error("Invalid query parameter");
			resp.status(400);
			return item;
		}
	}

	private static CommerceItem addItem(Integer quantity, String prodId, ShopingCart cart, Response resp){
		Product product = getProduct(prodId);
		CommerceItem item = new CommerceItem();
		if(product != null){
			BigDecimal amount = product.getPrice().multiply(new BigDecimal(quantity));
			
			item.setId(UUID.randomUUID().toString());
			item.setProduct_id(prodId);
			item.setQuantity(quantity);
			item.setAmount(amount);
		}else{
			logger.error("Product not found");
			resp.status(404);
			return item;
		}
		
		cart.addItem(item);
		return item;
	}
	
	private static CommerceItem updateItem(Integer quantity, CommerceItem item){
		Integer newQuantity = Integer.sum(item.getQuantity(), quantity);
		Product product = getProduct(item.getProduct_id());
		BigDecimal amount = product.getPrice().multiply(new BigDecimal(newQuantity));

		item.setQuantity(newQuantity);
		item.setAmount(amount);
		return item;
	}

	public static String rmItem(Request req, Response resp){		
		ShopingCart cart = getShoppingCart(req);
		Boolean removed = false;
		String commerceId = req.params("id");

		if(cart != null && cart.getItems() != null && cart.getItems().size() > 0  && commerceId != null){
			removed = cart.rmItem(commerceId);			
		}else{
			logger.error("Error to remove the item");
		}

		if(removed){
			return "";
		}else{
			resp.status(404);
			return "Item not found";
		}
		
	}

}
