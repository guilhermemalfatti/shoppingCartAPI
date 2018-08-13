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
		port(getHerokuAssignedPort());

		get("/products", (req, res) -> Controller.getProducts(), gson::toJson);

		get("/shoppingcart", (req, res) -> Controller.getShoppingCart(req), gson::toJson);
		
		post("/shoppingcart/items", (req, res) -> Controller.addItem(req, res), gson::toJson);
		
		post("/shoppingcart/items/:id", (req, res) -> Controller.rmItem(req, res), gson::toJson);
	}

	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
//./mvnw spring-boot:run
