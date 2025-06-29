package com.example.spring_boot_store_management_api.service;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.exception.ResourceNotFoundException;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

    @Service
    @AllArgsConstructor
    public class CheeseStockService {

        private final CheeseProductRepository cheeseProductRepository;
        private static final int RESTOCK_THRESHOLD = 3;

        /**
         * Check how many units are left for the given product.
         *
         * @param cheeseName name of the cheese product
         * @return "restock" when the product has less units available than specified,
         *         "sufficient stock" when the number of units is equal or greater than the specified number
         *         or the ResourceNotFound exception
         */
        public String checkStock(String cheeseName) {
            CheeseProduct product = cheeseProductRepository.findByCheeseName(cheeseName)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "name", cheeseName));

            int units = product.getStockUnits();

            return units <= RESTOCK_THRESHOLD ? "restock" : "sufficient stock";
        }
    }

