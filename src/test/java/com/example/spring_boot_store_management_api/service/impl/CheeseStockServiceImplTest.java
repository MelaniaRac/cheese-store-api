package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.exception.ProductNotFoundException;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheeseStockServiceImplTest {

    @Mock
    private CheeseProductRepository cheeseProductRepository;

    @InjectMocks
    private CheeseStockServiceImpl cheeseStockServiceImpl;

    @Test
    void checkStockReturnsRestockWhenBelowThreshold() {
        CheeseProduct product = new CheeseProduct("Brie", new BigDecimal("10"), 2);
        when(cheeseProductRepository.findByCheeseName("Brie")).thenReturn(Optional.of(product));

        String result = String.valueOf(cheeseStockServiceImpl.checkStock("Brie"));

        assertEquals("restock", result);
    }

    @Test
    void checkStockReturnsSufficientStockWhenAboveThreshold() {
        CheeseProduct product = new CheeseProduct("Camembert", new BigDecimal("10"), 5);
        when(cheeseProductRepository.findByCheeseName("Camembert")).thenReturn(Optional.of(product));

        String result = String.valueOf(cheeseStockServiceImpl.checkStock("Camembert"));

        assertEquals("sufficient stock", result);
    }

    @Test
    void checkStockThrowsResourceNotFoundWhenProductMissing() {
        when(cheeseProductRepository.findByCheeseName("Unknown")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cheeseStockServiceImpl.checkStock("Unknown"));
    }
}