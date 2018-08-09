package com.br.mcfadyen.shoppindCartAPI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.br.mcfadyen.shoppindCartAPI.manager.Controller;

@SpringBootApplication
public class ShoppindCartApiApplication {

	public static void main(String[] args) {
		Controller.appSetup();
		port(8080);

		get("/products", (req, res) -> {
			return Controller.getProducts();
		});

		
		get("/shoppingcart", (req, res) -> {			
			return Controller.getShoppingCart(req);
		});
		
	}
}
//./mvnw spring-boot:run
