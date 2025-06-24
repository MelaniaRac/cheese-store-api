package com.example.spring_boot_store_managament_api;

import com.example.spring_boot_store_managament_api.bean.CheeseProduct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStoreManagamentApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootStoreManagamentApiApplication.class, args);

		CheeseProduct cheese1 = new CheeseProduct("Cantal", 27);
		// invoking default constructor
		CheeseProduct cheese2 = new CheeseProduct();
		cheese2.setPrice(27);
		System.out.println(cheese1.toString());
		System.out.println(cheese1.equals(cheese2));

	}

}
