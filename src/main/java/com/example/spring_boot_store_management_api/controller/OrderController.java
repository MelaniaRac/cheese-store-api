package com.example.spring_boot_store_management_api.controller;

import com.example.spring_boot_store_management_api.dto.OrderCreateDto;
import com.example.spring_boot_store_management_api.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

// TODOo take into account that an order can have multiple cheeses
// and the same cheese can be present in multiple orders

@RestController
@RequestMapping("orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    // http://localhost:2028/orders/create
    // TODOo add customer entity and define create/customers/{customerId/orders}
    @PostMapping("create")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    // TODOo: check the user can see his address in the creation response message
    public ResponseEntity<OrderCreateDto> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        OrderCreateDto orderCreated = orderService.createOrder(orderCreateDto);

        return new ResponseEntity<>(orderCreated, HttpStatus.CREATED);
    }

    // http://localhost:2028/orders/revenue/2024-06-30
    @GetMapping("revenue/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getDailyRevenue(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        BigDecimal revenue = orderService.getDailyRevenue(date);

        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

}
