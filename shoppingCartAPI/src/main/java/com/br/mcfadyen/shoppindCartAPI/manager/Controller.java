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

		createProduct("001", "cadeira", "www.img.com.br", new BigDecimal("20.00"));
		createProduct("002", "mesa", "www.img.com.br", new BigDecimal("10.00"));
		
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

	public static CommerceItem addItem(Request req, Response resp){
		Integer quantity = Integer.valueOf(req.queryParams(QUANTITY).equals("") ? "0" : req.queryParams(QUANTITY));
		String prodId = req.queryParams(PRODUCT_ID);
		CommerceItem item = new CommerceItem();
		Product product = null;
		ShopingCart cart = getShoppingCart(req);

		if(prodId != null && quantity != null && quantity > 0){
			product = getProduct(prodId);
		}else{
			logger.error("Invalid query parmeter");
			resp.status(400);
			return item;
		}

		if(product != null){
			BigDecimal amount = product.getPrice().multiply(new BigDecimal(quantity));

			item = new CommerceItem();
			item.setId(UUID.randomUUID().toString());
			item.setProduct_id(product.getId());
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

	public static String rmItem(Request req, Response resp){		
		ShopingCart cart = getShoppingCart(req);
		Boolean removed = false;
		String commerceId = req.params("id");

		if(cart != null && cart.getItems().size() > 0  && commerceId != null){
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
