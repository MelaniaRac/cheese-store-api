package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.exception.InvalidUpdateException;
import com.example.spring_boot_store_management_api.exception.ResourceNotFoundException;
import com.example.spring_boot_store_management_api.mapper.CheeseProductMapper;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheeseProductImpl implements CheeseProductService {

    // this impl class has only one parameterized constructor => no @Autowired necessary for this dependency injection
    private CheeseProductRepository cheeseProductRepository;

    @Override
    public CheeseProductDto createCheeseProduct(CheeseProductDto cheeseProductDto) {
        // convert cheese DTO into JPA cheese entity
        CheeseProduct cheeseProductEntity = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProduct(cheeseProductDto);
        // save entity into a database
        CheeseProduct savedCheeseProduct = cheeseProductRepository.save(cheeseProductEntity);
        // convert JPA entity into Dto = bc we need to return as a response the Dto to the controller layer
        CheeseProductDto savedCheeseProductDto = CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(savedCheeseProduct);

        return savedCheeseProductDto;
    }

    @Override
    public CheeseProductDto findByCheeseName(String cheeseName) {
        CheeseProduct cheeseProduct = cheeseProductRepository.findByCheeseName(cheeseName).orElseThrow(
                // implement supplier functional interface
                () -> new ResourceNotFoundException("Product", "name", cheeseName)
        );

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProduct);
    }


    @Override
    public List<CheeseProductDto> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice) {
        List<CheeseProduct> cheeseProductList = cheeseProductRepository.findByStockUnitsAndRetailPriceLessThan(stockUnits, retailPrice);

        if(cheeseProductList.isEmpty()){
            throw new ResourceNotFoundException("Product", "criteria", "stock units = " + stockUnits + " and " + "price lower than " + retailPrice.toString());
        }

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProductList);
    }


    @Override
    // Jackson parses the JSON to the DTO object, matches the JSON keys to the DTO's
    // then, it populates the DTO and send it to this function
    public CheeseProductDto updateProductByPrice(CheeseProductDto productDto) {
        CheeseProduct productSearched = cheeseProductRepository.findByCheeseName(productDto.getCheeseName()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "name", productDto.getCheeseName())
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
        CheeseProduct productPriceUpdatedSaved = cheeseProductRepository.save(productSearched);

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(productPriceUpdatedSaved);
    }


    @Override
    public long deleteByStockUnits(Integer stockUnits) {
        Long numberDeletedRows = cheeseProductRepository.deleteByStockUnits(stockUnits);

        if (numberDeletedRows == 0) {
            throw new ResourceNotFoundException("Product", "stock units", stockUnits.toString());
        }

        return numberDeletedRows;
    }
}
