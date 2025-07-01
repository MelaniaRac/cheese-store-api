package com.example.spring_boot_store_management_api.service;

import com.example.spring_boot_store_management_api.dto.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrderService {

    BigDecimal getDailyRevenue(LocalDate date);

    /**
     * Persist an order and update stock units for the ordered products.
     *
     * @param orderDto        order data without sensitive address information
     * @param deliveryAddress address to deliver the order to
     * @return created order
     */
    OrderDto createOrder(OrderDto orderDto, String deliveryAddress);
}
