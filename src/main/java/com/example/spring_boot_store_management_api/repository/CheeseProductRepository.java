package com.example.spring_boot_store_management_api.repository;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// @Repository not needed because JpaRepository already contains this annotation
// by simply extending, our class gets all methods from JpaRepository
public interface CheeseProductRepository extends JpaRepository<CheeseProduct, Integer> {

// after Spring will parse every query method, the application context will start
// FIELDS THAT ARE PART OF THE METHOD NAME HAVE TO BE THE SAME AS THE ACTUAL ENTITY FIELDS
        /**
         * Find a product by its cheese name.
         *
         * @param cheeseName name to search for
         * @return matching product or empty if none found
         */
        public Optional<CheeseProduct> findByCheeseNameIgnoreCase(String cheeseName);

        /**
         * Find all cheese products with the given stockUnits and a price below the supplied value.
         *
         * @param stockUnits number of units in stock to match
         * @param retailPrice maximum price (exclusive)
         * @return matching cheese products
         */
        public List<CheeseProduct> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice);

        /**
         * Deletes all products whose stockUnits equals a given value.
         * @param stockUnits number pf stock units
         * @return the number of rows deleted
         */
        @Transactional
        @Modifying
        // non-primitive value needed
        public long deleteByStockUnits(Integer stockUnits);

}


