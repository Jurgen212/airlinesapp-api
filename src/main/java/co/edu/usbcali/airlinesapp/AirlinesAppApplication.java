package co.edu.usbcali.airlinesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirlinesAppApplication {


	public String PORT = System.getenv("PORT");

	public static void main(String[] args) {
		SpringApplication.run(AirlinesAppApplication.class, args);
	}
}
