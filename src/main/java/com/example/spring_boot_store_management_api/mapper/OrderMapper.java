package com.example.spring_boot_store_management_api.mapper;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.dto.OrderDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.entity.Order;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static interface  AutoOrderMapper{

        OrderMapper.AutoOrderMapper MAPPER = Mappers.getMapper(OrderMapper.AutoOrderMapper.class);

        OrderDto mapToOrderDto(Order order);

        Order mapToOrder(OrderDto orderDto);

        default List<OrderDto> mapToOrderDto(List<Order> orders) {
            return orders.stream()
                    .map(this::mapToOrderDto)
                    .collect(Collectors.toList());
        }
    }
}
