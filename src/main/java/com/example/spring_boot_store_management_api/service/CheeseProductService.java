package com.example.spring_boot_store_management_api.service;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;

import java.math.BigDecimal;
import java.util.List;

public interface CheeseProductService {

    /**
     * Add a product.
     *
     * @param cheeseProductDto added
     * @return created product
     */
    CheeseProductDto createCheeseProduct(CheeseProductDto cheeseProductDto);


    /**
     * Find a product by its cheese name.
     *
     * @param cheeseName name to search for
     * @return matching product or empty if none found
     */
    CheeseProductDto findCheese(String cheeseName);


    /**
     * Update ONLY the price of a product.
     *
     * @param product to update
     * @return product with new price or no modification if none found
     */
// TODOo having cheesename just because it's a good REST practice to separate the body from the resource identifier?
    CheeseProductDto patchProduct(String cheeseName, CheeseProductDto product);


    /**
     * Find all cheese products with a given stockUnits and a price below the desired value.
     *
     * @param stockUnits number of units in stock to match
     * @param retailPrice maximum price (excluded)
     * @return matching cheese products
     */
    List<CheeseProductDto> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice);


    /**
     * Deletes all products whose stockUnits equals a given value.
     * @param stockUnits number pf stock units
     * @return number of deleted products
     */
    long deleteByStockUnits(Integer stockUnits);
}
