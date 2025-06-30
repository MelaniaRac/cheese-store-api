package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.exception.ResourceNotFoundException;
import com.example.spring_boot_store_management_api.mapper.CheeseProductMapper;
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
class CheeseProductImplTest {

    @Mock
    private CheeseProductRepository cheeseProductRepository;

    @Mock
    private CheeseStockServiceImpl cheeseStockServiceImpl;

    @InjectMocks
    private CheeseProductImpl cheeseProductService;

    @Test
    void createCheeseProductAddsWarningWhenLowStock() {
        CheeseProductDto dto = new CheeseProductDto("Tome", new BigDecimal("55"), 2);
        CheeseProduct entity = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProduct(dto);
        when(cheeseProductRepository.save(any(CheeseProduct.class))).thenReturn(entity);
        when(cheeseStockServiceImpl.checkStock("Tome")).thenReturn(CheeseStockServiceImpl.StockLevel.valueOf("restock"));

        CheeseProductDto result = cheeseProductService.createCheeseProduct(dto);

        assertEquals("Gouda", result.getCheeseName());
        assertEquals("⚠️ Warning: Stock is low (below 4 units). Consider restocking.", result.getWarningMessage());
    }

    @Test
    void findByCheeseNameThrowsExceptionWhenNotFound() {
        when(cheeseProductRepository.findByCheeseName("Bleu")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cheeseProductService.findByCheeseName("Bleu"));
    }

    @Test
    void updateProductByPriceUpdatesPrice() {
        CheeseProduct existingEntity = new CheeseProduct("Livarot", new BigDecimal("48"), 20);
        CheeseProductDto updateDto = new CheeseProductDto("Livarot", new BigDecimal("48"), 20);
        when(cheeseProductRepository.findByCheeseName("Livarot")).thenReturn(Optional.of(existingEntity));
        when(cheeseProductRepository.save(any(CheeseProduct.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CheeseProductDto result = cheeseProductService.updateProductByPrice(updateDto);

        assertEquals(new BigDecimal("48"), result.getRetailPrice());
    }

    @Test
    void deleteByStockUnitsThrowsExceptionWhenNoneDeleted() {
        when(cheeseProductRepository.deleteByStockUnits(0)).thenReturn(0L);

        assertThrows(ResourceNotFoundException.class, () -> cheeseProductService.deleteByStockUnits(0));
    }
}
