package com.example.spring_boot_store_management_api.mapper;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.dto.OrderCreateDto;
import com.example.spring_boot_store_management_api.dto.OrderDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.entity.Order;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static interface  AutoOrderMapper{

        OrderMapper.AutoOrderMapper MAPPER = Mappers.getMapper(OrderMapper.AutoOrderMapper.class);

        // will be used for Get order, where the sensitive address info for ADMIN and USER wil be hidden
        OrderDto mapToOrderDto(Order order);

        Order mapToOrder(OrderDto orderDto);

        Order mapToOrder(OrderCreateDto orderCreateDto);

        OrderCreateDto mapToOrderCreateDto(Order order);
    }
}
