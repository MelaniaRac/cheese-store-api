package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.exception.ProductNotFoundException;
import com.example.spring_boot_store_management_api.mapper.CheeseProductMapper;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CheeseProductImpl implements CheeseProductService {

    // this impl class has only one parameterized constructor => no @Autowired necessary for this dependency injection
    // now I added another one, so ...Autowired
    @Autowired
    private CheeseProductRepository cheeseProductRepository;
    private CheeseStockServiceImpl cheeseStockServiceImpl;

    @Override
    public CheeseProductDto createCheeseProduct(CheeseProductDto cheeseProductDto) {
        // check if a cheese with the same name already exists
        boolean exists = cheeseProductRepository.findByCheeseNameIgnoreCase(cheeseProductDto.getCheeseName()).isPresent();
        if (exists) {
            // "TODOo" replace it with "Product cannot be created" to not be misleading
            throw new ProductNotFoundException("Product", "name. Product already exists.");
        }
        // "TODOo" @Positive validation for price and stockUnits
        // "TODOo" make sure the name is not empty

        // convert cheese DTO into JPA cheese entity
        CheeseProduct cheeseProductEntity = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProduct(cheeseProductDto);
        // save entity into a database
        CheeseProduct savedCheeseProduct = cheeseProductRepository.save(cheeseProductEntity);
        // convert JPA entity into Dto because we need to return as a response the Dto to the controller layer
        CheeseProductDto savedCheeseProductDto = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(savedCheeseProduct);

        // check if the stock for the added product is below the threshold
        var stockWarning = cheeseStockServiceImpl.checkStock(savedCheeseProductDto.getCheeseName());

        if ("restock".equalsIgnoreCase(String.valueOf(stockWarning))){
            savedCheeseProductDto.setWarningMessage("⚠️ Warning: Stock is low. Consider restocking.");
        }

        return savedCheeseProductDto;
    }

    @Override
    public CheeseProductDto findCheese(String cheeseName) {
        var cheeseProduct = cheeseProductRepository.findByCheeseNameIgnoreCase(cheeseName).orElseThrow(
//                JPA can’t guarantee that a product with this name exists
//                => instead of returning null, which can easily cause NullPointerException,
//                it wraps the result in Optional

                // implement supplier functional interface
                () -> new ProductNotFoundException("Product", "name")
        );

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProduct);
    }


    @Override
    public List<CheeseProductDto> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice) {
        List<CheeseProduct> cheeseProductList = cheeseProductRepository.findByStockUnitsAndRetailPriceLessThan(stockUnits, retailPrice);

        if(cheeseProductList.isEmpty()){
            throw new ProductNotFoundException("Product", "criteria");
        }

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProductList);
    }


    @Override
    // Jackson parses the JSON to the DTO object, matches the JSON keys to the DTO's
    // then, it populates the DTO and sends it to this function
    public CheeseProductDto patchProduct(String cheeseName, CheeseProductDto productDto) {
        var productEntity = cheeseProductRepository.findByCheeseNameIgnoreCase(cheeseName)
                .orElseThrow( () -> new ProductNotFoundException("Product", "name")
                );

        // built-in utility instead of checking if every field is defined
        final BeanWrapper source = new BeanWrapperImpl(productDto);

        String[] nullPropertyNames = Arrays.stream(source.getPropertyDescriptors()).map(PropertyDescriptor::getName)
                .filter(name -> source.getPropertyValue(name) == null)
                        .toArray(String[]::new);

        BeanUtils.copyProperties(productDto, productEntity, nullPropertyNames);
        //
        System.out.println(productEntity);
        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER
                .mapToCheeseProductDto(cheeseProductRepository.save(productEntity));
    }

    // TODOo updateEntireProduct (all fields have to be mentioned)

    @Override
    public long deleteByStockUnits(Integer stockUnits) {
        var numberDeletedRows = cheeseProductRepository.deleteByStockUnits(stockUnits);

        if (numberDeletedRows == 0) {
            throw new ProductNotFoundException("Product", "stock units.");
        }

        return numberDeletedRows;
    }
}
