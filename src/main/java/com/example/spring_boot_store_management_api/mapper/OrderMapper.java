package com.example.spring_boot_store_management_api.mapper;

import com.example.spring_boot_store_management_api.dto.OrderCreateDto;
import com.example.spring_boot_store_management_api.entity.Order;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
@Mapper
    public static interface  AutoOrderMapper{

        AutoOrderMapper MAPPER = Mappers.getMapper(OrderMapper.AutoOrderMapper.class);

        // will be used for Get order, where the sensitive address info for ADMIN and USER wil be hidden
        OrderCreateDto mapToOrderCreateDto(Order order);

        Order mapToOrder(OrderCreateDto orderCreateDto);

    }
}
