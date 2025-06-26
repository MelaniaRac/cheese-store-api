package com.example.spring_boot_store_management_api.repository;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// WE USE THIS TO RUN THE CODE, NOT TEST IT
//@DataJpaTest

// annotation to load a full application context so we can inject any spring bean in this class
@SpringBootTest
class CheeseProductRepositoryTest {

    @Autowired
    private CheeseProductRepository cheeseProductRepository;

    @Test
    void addProduct(){
        // create product
        CheeseProduct cheese1 = new CheeseProduct();
        cheese1.setCheeseName("Morbier");
        cheese1.setPrice(new BigDecimal("60"));
        cheese1.setStockUnits(10);
        // add product
        // uses Hibernate internally to generate primaryKey
        CheeseProduct newProduct = cheeseProductRepository.save(cheese1);
        // display product
        System.out.println(newProduct.getCheeseId());
        System.out.println(newProduct.toString());
    }
}