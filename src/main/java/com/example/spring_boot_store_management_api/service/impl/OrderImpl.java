package com.example.spring_boot_store_management_api.service.impl;

import com.example.spring_boot_store_management_api.dto.OrderCreateDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.entity.Order;
import com.example.spring_boot_store_management_api.exception.OutOfStockException;
import com.example.spring_boot_store_management_api.exception.ProductNotFoundException;
import com.example.spring_boot_store_management_api.mapper.OrderMapper;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.repository.OrderRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import com.example.spring_boot_store_management_api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderImpl implements OrderService {

    OrderRepository orderRepository = null;
    CheeseProductRepository cheeseProductRepository = null;
    CheeseStockServiceImpl cheeseProductStock = null;


    @Override
    public OrderCreateDto createOrder(OrderCreateDto orderCreateDto){

        BigDecimal total = BigDecimal.ZERO;

        // the OrderItemDto elements inside the orderCreateDto list are automatically mapped to orderItem
        // because of the structure of Order and OrderItem
        Order order = OrderMapper.AutoOrderMapper.MAPPER.mapToOrder(orderCreateDto);
        order.setDeliveryAddress(order.getDeliveryAddress());

        for (var item : order.getOrderedProducts()) {
            // the 'might throw NullPointer' for findCheeseName is IDE-level caution, not compiler error
            // TODOo: is it a better practice to look by id instead of cheeseName
            // -> faster DB search, names are not necessarily unique
            // after I create the front end that translates names into IDs
            CheeseProduct product = cheeseProductRepository.findByCheeseNameIgnoreCase(item.getCheeseName())
                    .orElseThrow(() -> new ProductNotFoundException("Product", "name"));

            // TODOo the user can order at least 1 unit

            if (item.getOrderedUnits() > product.getStockUnits()){
                // only one outOfStock error per product is shown at a time
                throw new OutOfStockException(
                        // TODOo the equal case not correctly treated
                        product.getCheeseName()
                );
            }
            product.setStockUnits(product.getStockUnits() - item.getOrderedUnits());
            cheeseProductRepository.save(product);
            cheeseProductStock.checkStock(product.getCheeseName());


            total = total.add(product.getRetailPrice().multiply(BigDecimal.valueOf(item.getOrderedUnits())));
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
