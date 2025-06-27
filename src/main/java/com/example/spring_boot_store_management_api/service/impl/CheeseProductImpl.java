package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CheeseProductImpl implements CheeseProductService {

    // this impl class has only one parameterized constructor => no @Autowired necessary for this dependency injection
    private CheeseProductRepository cheeseProductRepository;

    @Override
    public CheeseProduct createCheeseProduct(CheeseProduct cheeseProduct) {
        return cheeseProductRepository.save(cheeseProduct);
    }

    @Override
    public CheeseProduct findByCheeseName(String cheeseName) {
        return cheeseProductRepository.findByCheeseName(cheeseName);
    }

    @Override
    public CheeseProduct updateProductByPrice(CheeseProduct product) {
        CheeseProduct productSearched = cheeseProductRepository.findByCheeseName(product.getCheeseName());
        productSearched.setRetailPrice(product.getRetailPrice());

        return cheeseProductRepository.save(productSearched);
    }

    @Override
    public List<CheeseProduct> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice) {
        return cheeseProductRepository.findByStockUnitsAndRetailPriceLessThan(stockUnits, retailPrice);
    }

    @Override
    public long deleteByStockUnits(Integer stockUnits) {
        return cheeseProductRepository.deleteByStockUnits(stockUnits);
    }
}
