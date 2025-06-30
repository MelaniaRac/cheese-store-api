package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.exception.ResourceNotFoundException;
import com.example.spring_boot_store_management_api.mapper.CheeseProductMapper;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import com.example.spring_boot_store_management_api.service.CheeseStockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CheeseProductImpl implements CheeseProductService {

    // this impl class has only one parameterized constructor => no @Autowired necessary for this dependency injection
    // now I added another one, so ...Autowired
    @Autowired
    private CheeseProductRepository cheeseProductRepository;
    private CheeseStockService cheeseStockService;

    @Override
    public CheeseProductDto createCheeseProduct(CheeseProductDto cheeseProductDto) {
        // check if a cheese with the same name already exists
        boolean exists = cheeseProductRepository.findByCheeseName(cheeseProductDto.getCheeseName()).isPresent();
        if (exists) {
            // TODO replace it with "Duplicate entry" to not be misleading
            throw new ResourceNotFoundException("Product", "name. Product already exists.");
        }

        // convert cheese DTO into JPA cheese entity
        CheeseProduct cheeseProductEntity = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProduct(cheeseProductDto);
        // save entity into a database
        CheeseProduct savedCheeseProduct = cheeseProductRepository.save(cheeseProductEntity);
        // convert JPA entity into Dto because we need to return as a response the Dto to the controller layer
        CheeseProductDto savedCheeseProductDto = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(savedCheeseProduct);

        // check if the stock for the added product is below the threshold
        var stockWarning = cheeseStockService.checkStock(savedCheeseProductDto.getCheeseName());

        if ("restock".equalsIgnoreCase(String.valueOf(stockWarning))){
            savedCheeseProductDto.setWarningMessage("⚠️ Warning: Stock is low. Consider restocking.");
        }

        return savedCheeseProductDto;
    }

    @Override
    public CheeseProductDto findByCheeseName(String cheeseName) {
        var cheeseProduct = cheeseProductRepository.findByCheeseName(cheeseName).orElseThrow(
                // implement supplier functional interface
                () -> new ResourceNotFoundException("Product", "name")
        );

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProduct);
    }


    @Override
    public List<CheeseProductDto> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice) {
        List<CheeseProduct> cheeseProductList = cheeseProductRepository.findByStockUnitsAndRetailPriceLessThan(stockUnits, retailPrice);

        if(cheeseProductList.isEmpty()){
            throw new ResourceNotFoundException("Product", "criteria");
        }

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProductList);
    }


    @Override
    // Jackson parses the JSON to the DTO object, matches the JSON keys to the DTO's
    // then, it populates the DTO and send it to this function
    public CheeseProductDto updateProductByPrice(CheeseProductDto productDto) {
        var productSearched = cheeseProductRepository.findByCheeseName(productDto.getCheeseName()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "name")
                );

//        // Only price can be changed
//        if (productSearched.getCheeseName() != productDto.getCheeseName()) {
//            throw new InvalidUpdateException("You cannot change the cheese name. Only the price can be changed.");
//        }
//        if (productSearched.getStockUnits() != productDto.getStockUnits()) {
//            throw new InvalidUpdateException("You cannot change the stock units. Only the price can be changed.");
//        }

        // the set method should be used only in the controller layer
        productSearched.setRetailPrice(productDto.getRetailPrice());
        var productPriceUpdatedSaved = cheeseProductRepository.save(productSearched);

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(productPriceUpdatedSaved);
    }


    @Override
    public long deleteByStockUnits(Integer stockUnits) {
        var numberDeletedRows = cheeseProductRepository.deleteByStockUnits(stockUnits);

        if (numberDeletedRows == 0) {
            throw new ResourceNotFoundException("Product", "stock units.");
        }

        return numberDeletedRows;
    }
}
