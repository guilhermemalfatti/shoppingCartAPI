package com.br.mcfadyen.shoppindCartAPI.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.br.mcfadyen.shoppindCartAPI.model.Product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller {
	private static List<Product> products = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger(Controller.class);

	private static void createProduct(String id, String name, String image, BigDecimal price){
		Product product = new Product();
		product.setId(id);
		product.setImage(image);
		product.setName(name);
		product.setPrice(price);

		if(product != null && products != null){
			products.add(product);
		}{
			//TODO
			//log something
		}
		
	}

	public static void createProducts(){

		createProduct("001", "cadeira", "www.img.com.br", new BigDecimal("55.55"));
		createProduct("002", "mesa", "www.img.com.br", new BigDecimal("99.55"));
	}

	public static String getProducts() {
		logger.info("info test");
		return products.toString();
	}

}
