package com.example.spring_boot_store_management_api.repository;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// WE USE THIS TO RUN THE CODE, NOT TEST IT
//@DataJpaTest

// annotation to load a full application context so we can inject any spring bean in this class
@SpringBootTest
class CheeseProductRepositoryTest {

    @Autowired
    private CheeseProductRepository cheeseProductRepository;

    List<CheeseProduct> products = List.of(
        new CheeseProduct("Selles-sur-Cher", new BigDecimal("60"), 10),
        new CheeseProduct( "Picodon", new BigDecimal("50"), 8),
        new CheeseProduct( "Mont d’Or", new BigDecimal("70"), 8),
        new CheeseProduct( "Sainte-Maure de Touraine", new BigDecimal("30"), 5),
        new CheeseProduct( "Saint-Nectaire", new BigDecimal("25.5"), 20),
        new CheeseProduct( "Tome des Bauges", new BigDecimal("35.5"), 11)
    );

    @Test
    void addMultipleProducts(){

        cheeseProductRepository.saveAll(products);
    }

}