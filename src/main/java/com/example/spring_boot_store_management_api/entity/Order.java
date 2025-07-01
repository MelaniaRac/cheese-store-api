package com.example.spring_boot_store_management_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @NonNull
    private BigDecimal totalValue;

    @NonNull
    private LocalDate orderDate;// to be used for service function revenuePerDay

    @NonNull
    // sensitive information => not going to be in the DTO
    private String deliveryAddress;

    // order contains a collection of orderItem elements
    @ElementCollection // collection of value types, not entities
    @CollectionTable(name = "ordered_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderedProducts;
}
