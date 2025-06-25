package com.example.spring_boot_store_management_api;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStoreManagementApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootStoreManagementApiApplication.class, args);

		CheeseProduct cheese1 = new CheeseProduct("Cantal", 27);
		// invoking default constructor
		CheeseProduct cheese2 = new CheeseProduct("Bleu d'Auvergne", 27);

		System.out.println(cheese1.toString());
		System.out.println(cheese1.equals(cheese2));

	}

}
