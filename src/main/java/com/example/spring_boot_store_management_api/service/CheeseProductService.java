package com.example.spring_boot_store_management_api.service;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;

import java.math.BigDecimal;
import java.util.List;

public interface CheeseProductService {

    CheeseProduct createCheeseProduct(CheeseProduct cheeseProduct);

    CheeseProduct findByCheeseName(String cheeseName);

    CheeseProduct updateProductByPrice(CheeseProduct product);

    List<CheeseProduct> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice);
}
