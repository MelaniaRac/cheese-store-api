package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.OrderCreateDto;
import com.example.spring_boot_store_management_api.dto.OrderDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.entity.Order;
import com.example.spring_boot_store_management_api.exception.ResourceNotFoundException;
import com.example.spring_boot_store_management_api.mapper.OrderMapper;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.repository.OrderRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import com.example.spring_boot_store_management_api.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderImpl implements OrderService {

    OrderRepository orderRepository = null;
    CheeseProductRepository cheeseProductRepository = null;
    CheeseStockServiceImpl cheeseProductStock = null;


    @Override
    public OrderCreateDto createOrder(OrderCreateDto orderCreateDto){

        BigDecimal total = BigDecimal.ZERO;

        Order order = OrderMapper.AutoOrderMapper.MAPPER.mapToOrder(orderCreateDto);
        order.setDeliveryAddress(order.getDeliveryAddress());

        for (var item : order.getOrderedProducts()) {
            // the 'might throw NullPointer' for findCheeseName is IDE-level caution, not compiler error
            CheeseProduct product = cheeseProductRepository.findByCheeseName(item.getCheeseName())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "name"));
            // TODOo make units from order impossible to be greater than stockUnits
            if (item.getUnits() > product.getStockUnits()){
                throw new IllegalArgumentException(
                        "Requested units exceed available stock for " + product.getCheeseName()
                );
            }
            product.setStockUnits(product.getStockUnits() - item.getUnits());
            cheeseProductRepository.save(product);
            cheeseProductStock.checkStock(product.getCheeseName());

            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getUnits())));
        }

        order.setTotalValue(total);
        order.setOrderDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.AutoOrderMapper.MAPPER.mapToOrderCreateDto(savedOrder);
    }


    @Override
    public BigDecimal getDailyRevenue(LocalDate date) {

        List<Order> orders = orderRepository.findByOrderDate(date);
        return orders.stream()
                .map(Order::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
