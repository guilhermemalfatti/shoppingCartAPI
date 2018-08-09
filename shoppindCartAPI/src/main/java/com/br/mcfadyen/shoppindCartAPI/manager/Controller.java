package com.br.mcfadyen.shoppindCartAPI.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.br.mcfadyen.shoppindCartAPI.model.Product;
import com.br.mcfadyen.shoppindCartAPI.model.ShopingCart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.client.HttpRequest;

import spark.Request;
/**
 * Class that have controller method os the application
 */
public class Controller {
	//the list of mock product
	private static List<Product> products = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger(Controller.class);
	private static final String SHOPPING_CART = "shopingCart";

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

		createProduct("001", "cadeira", "www.img.com.br", new BigDecimal("55.55"));
		createProduct("002", "mesa", "www.img.com.br", new BigDecimal("99.55"));
		
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
			cart = new ShopingCart();
			req.session().attribute(SHOPPING_CART, cart);
		}
		return cart;
	}

}
