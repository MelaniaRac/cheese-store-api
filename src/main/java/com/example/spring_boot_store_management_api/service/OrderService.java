package com.example.spring_boot_store_management_api.service;

import com.example.spring_boot_store_management_api.dto.OrderCreateDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrderService {

    BigDecimal getDailyRevenue(LocalDate date);

    /**
     * Persist an order launched by end user
     * Update stock units automatically for the ordered products.
     *
     * @param orderCreateDto      order data (includes sensitive address information)
     * @return created order
     */
    OrderCreateDto createOrder(OrderCreateDto orderCreateDto);
}
