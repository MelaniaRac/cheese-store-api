package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.mapper.CheeseProductMapper;
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
        CheeseProduct cheeseProduct = cheeseProductRepository.findByCheeseName(cheeseName);

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProduct);
    }

    @Override
    public CheeseProductDto updateProductByPrice(CheeseProductDto product) {
        // the set method should be used only in the controller layer
        CheeseProduct productSearched = cheeseProductRepository.findByCheeseName(product.getCheeseName());
        productSearched.setRetailPrice(product.getRetailPrice());
        CheeseProduct productPriceUpdatedSaved = cheeseProductRepository.save(productSearched);

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(productPriceUpdatedSaved);
    }

    @Override
    public List<CheeseProductDto> findByStockUnitsAndRetailPriceLessThan(int stockUnits, BigDecimal retailPrice) {
        List<CheeseProduct> cheeseProductList = cheeseProductRepository.findByStockUnitsAndRetailPriceLessThan(stockUnits, retailPrice);

        return CheeseProductMapper.AutoCheeseProductMapper.MAPPER.mapToCheeseProductDto(cheeseProductList);
    }

    @Override
    public long deleteByStockUnits(Integer stockUnits) {
        return cheeseProductRepository.deleteByStockUnits(stockUnits);
    }
}
