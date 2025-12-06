package com.example.spring_boot_store_management_api.entity;

import jakarta.persistence.*;
import lombok.*;

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
    // "int" type is dangerous for PATCH (bc default = 0) and Integer wouldn't provide as many unique ids as Long
    private Long orderId;

    // order contains a collection of orderItem elements
    @NonNull
    @ElementCollection // collection of value types, not entities
    @CollectionTable(name = "ordered_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderedProducts;

    @NonNull
    private String deliveryAddress;

    // TODOo field that should not be provided by end user; it should be automatically generated
    @NonNull
    private BigDecimal totalValue;

    // field that should not be provided by end user; it should be automatically generated
    // TODOo: modify RequestBody in createOrder OR modify OrderCreateDTO
    @NonNull
    private LocalDate orderDate;// to be used for service function revenuePerDay and for order history
}
