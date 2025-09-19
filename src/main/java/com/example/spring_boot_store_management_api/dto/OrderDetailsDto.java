//package com.example.spring_boot_store_management_api.dto;
//
//import com.example.spring_boot_store_management_api.entity.OrderItem;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
//
//// DTO for order details introduced by admin
//public class OrderDetailsDto {
//    // DTO is only the carrier, no need for these annotations here
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int orderId;
//
//    @NonNull
//    private BigDecimal totalValue;
//
//    @NonNull
//    // to be used for service function revenuePerDay
//    private LocalDate orderDate;
//
//    @NonNull
//    private List<OrderItem> orderedProducts;
//
//    @NonNull
//    // needed in the DTO because, in our scenario, the admin is the store owner
//    // who has to know the address to schedule the delivery
//    private String deliveryAddress;
//
//}
