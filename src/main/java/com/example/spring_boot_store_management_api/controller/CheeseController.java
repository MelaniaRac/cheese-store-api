package com.example.spring_boot_store_management_api.controller;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
// define base URL for defining the other REST APIs
@RequestMapping("cheese")
@AllArgsConstructor
public class CheeseController {

    private CheeseProductService cheeseProductService;

//    http://localhost:2028/cheese/create
    // build create cheese product REST API
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CheeseProduct> createCheeseProduct(@RequestBody CheeseProduct cheeseProduct){
        CheeseProduct productAdded = cheeseProductService.createCheeseProduct(cheeseProduct);

        return new ResponseEntity<>(productAdded, HttpStatus.CREATED);
    }

//    http://localhost:2028/cheese/Chabichou
    // build get product by CheeseName
    @GetMapping("{name}")
    public ResponseEntity<CheeseProduct> findByCheeseName(@PathVariable("name") String cheeseName){
        CheeseProduct productSearched = cheeseProductService.findByCheeseName(cheeseName);

        return new ResponseEntity<>(productSearched, HttpStatus.OK);
    }

//    http://localhost:2028/cheese/Valencay
    // build update price of chosen product
    @PutMapping("{cheeseName}")
    public ResponseEntity<CheeseProduct> updateCheese(@RequestBody CheeseProduct product, @PathVariable String cheeseName){
        product.setCheeseName(cheeseName);
        CheeseProduct productUpdated = cheeseProductService.updateProductByPrice(product);

        return new ResponseEntity<>(productUpdated, HttpStatus.OK);

    }

//     http//localhost:2028/cheese/stockUnits/retailPrice
    // build get products by stock units and price REST API
    @GetMapping("{stockUnits}/{retailPrice}")
    public ResponseEntity<List<CheeseProduct>> findByStockUnitsAndRetailPriceLessThan(@PathVariable int stockUnits,
                                                                                      @PathVariable BigDecimal retailPrice){
        List<CheeseProduct> productsSearched= cheeseProductService.findByStockUnitsAndRetailPriceLessThan(stockUnits, retailPrice);

        return new ResponseEntity<>(productsSearched, HttpStatus.OK);
    }


    // build delete product REST API
    @DeleteMapping("{stockUnits}")
    public ResponseEntity<String> deleteByStockUnits(@PathVariable("stockUnits") Integer stockUnits){
        long numberDeletedProducts = cheeseProductService.deleteByStockUnits(stockUnits);

        return new ResponseEntity<>("Products with stockUnits = 0 deleted:" + numberDeletedProducts, HttpStatus.OK);
    }
}
