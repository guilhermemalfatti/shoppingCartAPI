package com.br.mcfadyen.shoppindCartAPI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import com.google.gson.Gson;
import com.br.mcfadyen.shoppindCartAPI.manager.Controller;

@SpringBootApplication
public class ShoppindCartApiApplication {

	public static void main(String[] args) {
		Gson gson = new Gson();
		Controller.appSetup();
		port(8080);

		get("/products", (req, res) -> {
			return Controller.getProducts();
		});

		
		get("/shoppingcart", (req, res) -> {			
			return Controller.getShoppingCart(req);
		});
		
		post("/shoppingcart/items", (req, res) -> Controller.addItem(req, res), gson::toJson);
	}
}
//./mvnw spring-boot:run
