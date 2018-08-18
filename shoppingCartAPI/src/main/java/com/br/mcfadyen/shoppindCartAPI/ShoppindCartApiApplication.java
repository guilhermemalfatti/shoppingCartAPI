package com.br.mcfadyen.shoppindCartAPI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.options;
import static spark.Spark.before;
import com.google.gson.Gson;
import com.br.mcfadyen.shoppindCartAPI.manager.Controller;

@SpringBootApplication
public class ShoppindCartApiApplication {

	public static void main(String[] args) {
		Gson gson = new Gson();
		Controller.appSetup();
		port(getHerokuAssignedPort());
		
		options("/*", (request,response)->{
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if(accessControlRequestMethod != null){
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
	
			return "OK";
		});

		before((request, response) -> response.header("Access-Control-Allow-Origin", "https://shopping-cart-web-app.herokuapp.com"));
		before((request, response) -> response.header("Access-Control-Allow-Credentials", "true"));

		get("/products", (req, res) -> Controller.getProducts(), gson::toJson);

		get("/shoppingcart", (req, res) -> Controller.getShoppingCart(req), gson::toJson);
		
		post("/shoppingcart/items", (req, res) -> Controller.addOrUpdateItem(req, res), gson::toJson);
		
		delete("/shoppingcart/items/:id", (req, res) -> Controller.rmItem(req, res), gson::toJson);
	}

	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8081; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
//./mvnw spring-boot:run
