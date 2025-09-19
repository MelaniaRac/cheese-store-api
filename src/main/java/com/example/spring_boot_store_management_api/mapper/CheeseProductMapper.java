package com.example.spring_boot_store_management_api.mapper;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class CheeseProductMapper {
    @Mapper
    // mapStruct will create the implementation for them at compile time
    public static interface AutoCheeseProductMapper{
        // for mapping the fields from an entity to dto, the fields have to be the same
        // nevertheless, mapStruct has the possibility to map fields with different names

        // provides the implementation of the interface at compile time
        AutoCheeseProductMapper MAPPER = Mappers.getMapper(AutoCheeseProductMapper.class);

        // convert from JPA entity to DTO
        CheeseProductDto mapToCheeseProductDto(CheeseProduct cheeseProduct);

        // vice versa
        CheeseProduct mapToCheeseProduct(CheeseProductDto cheeseProductDto);

        // overload of mapToCheeseProductDto for handling a list of cheese products in serviceImpl
        default List<CheeseProductDto> mapToCheeseProductDto(List<CheeseProduct> products) {
            return products.stream()
                    .map(this::mapToCheeseProductDto)
                    .collect(Collectors.toList());
        }
    }
}
