package com.example.spring_boot_store_management_api.repository;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

// WE USE THIS TO RUN THE CODE, NOT TEST IT
//@DataJpaTest

// annotation to load a full application context so we can inject any spring bean in this class
@SpringBootTest
@Transactional
class CustomQueriesTest {

    @Autowired
    private CheeseProductRepository cheeseProductRepository;

    List<CheeseProduct> products = List.of(
        new CheeseProduct("Selles sur Cher", new BigDecimal("60"), 10),
        new CheeseProduct( "Picodon", new BigDecimal("10"), 1),
        new CheeseProduct( "NeufChatel", new BigDecimal("70"), 0),
        new CheeseProduct( "Sainte Maure de Touraine", new BigDecimal("19"), 1),
        // same price and stock unit
        new CheeseProduct( "Cas sarat", new BigDecimal("19"), 1),
        new CheeseProduct( "Saint Nectaire", new BigDecimal("15.5"), 0),
        new CheeseProduct( "Tome des Bauges", new BigDecimal("35.5"), 1)
    );

    //@BeforeEach
    void addMultipleProducts(){
        //cheeseProductRepository.deleteAll();
        cheeseProductRepository.saveAll(products);
    }

    @Test
    // find product by name
    void findByName(){

        CheeseProduct productFoundByName = cheeseProductRepository.findByCheeseName("NeufChatel");

        System.out.println(productFoundByName);
    }

    @Test
    void findByStockAndPrice(){
    // get products with stockUnit = 1 and retail price <= 20
        List<CheeseProduct> productStockPriceConstraint = cheeseProductRepository.findByStockUnitsAndRetailPriceLessThan(1, new BigDecimal("20"));

        System.out.println(productStockPriceConstraint);
    }

    // considered a modifying query = require an active transaction
    @Test
    @Rollback(value = false)
    void deleteProductWithStockUnit0(){

        Long numberProductsDeleted = cheeseProductRepository.deleteByStockUnits(0);
        System.out.println("Number of deleted products with StockUnit=0" + numberProductsDeleted.toString());

    }
}