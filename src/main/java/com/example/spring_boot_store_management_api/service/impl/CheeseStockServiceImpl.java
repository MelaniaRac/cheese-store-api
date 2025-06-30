package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.exception.ResourceNotFoundException;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

    @Service
    @AllArgsConstructor
    public class CheeseStockServiceImpl {

        private final CheeseProductRepository cheeseProductRepository;
        private static final int RESTOCK_THRESHOLD = 3;

        // just to play with java 17
        public enum StockLevel { RESTOCK, SUFFICIENT }

        /**
         * Check how many units are left for the given product.
         *
         * @param cheeseName name of the cheese product
         * @return "restock" when the product has less units available than specified,
         *         "sufficient stock" when the number of units is equal or greater than the specified number
         *         or the ResourceNotFound exception
         */
        public StockLevel checkStock(String cheeseName) {
            // Java 17 feature var (infers local variable types)
            var product = cheeseProductRepository.findByCheeseName(cheeseName)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "name"));

            // I overdid it just to play with java 17, could have just used ternary operator
            int stockVerification = (product.getStockUnits() <= RESTOCK_THRESHOLD) ? 0 : 1;

            return switch (stockVerification) {
                case 0 -> StockLevel.RESTOCK;
                case 1 -> StockLevel.SUFFICIENT;
                default -> throw new IllegalStateException("Unexpected value: " + stockVerification);
            };
        }
    }

