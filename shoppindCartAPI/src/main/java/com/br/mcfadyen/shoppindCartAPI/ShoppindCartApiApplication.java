package com.br.mcfadyen.shoppindCartAPI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.br.mcfadyen.shoppindCartAPI.manager.Controller;

@SpringBootApplication
public class ShoppindCartApiApplication {

	public static void main(String[] args) {
		port(8080);
		get("/test", (req, res) -> {
			return Controller.getProducts();
		});
	}
}
//./mvnw spring-boot:run
