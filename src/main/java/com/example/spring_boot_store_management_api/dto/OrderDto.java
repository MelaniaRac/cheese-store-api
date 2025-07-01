package com.example.spring_boot_store_management_api.dto;

import com.example.spring_boot_store_management_api.entity.OrderItem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderDto {
    // DTO is only the carrier, no need for these annotations here
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @NonNull
    private BigDecimal totalValue;
    @NonNull
    private LocalDate orderDate;// to be used for service function revenuePerDay
    @NonNull
    private List<OrderItem> orderedProducts;
}
