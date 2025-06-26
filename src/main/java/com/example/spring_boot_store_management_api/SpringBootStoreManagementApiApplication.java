package com.example.spring_boot_store_management_api;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringBootStoreManagementApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootStoreManagementApiApplication.class, args);

//		CheeseProduct cheese1 = new CheeseProduct("Cantal", new BigDecimal("28.8"),3);
//		// invoking default constructor
//		CheeseProduct cheese2 = new CheeseProduct("Bleu d'Auvergne", new BigDecimal("27"), 6);
//
//		System.out.println(cheese1.toString());
//		System.out.println(cheese1.equals(cheese2));

	}

}
