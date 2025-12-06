package com.example.spring_boot_store_management_api.dto;

import com.example.spring_boot_store_management_api.entity.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
//@RequiredArgsConstructor
// TODOo : should I have an OrderPlacedDto as well?
// TODOo : separate DTO for admin if I want to give him additional rights (ex:apply discount, order status)

// DTO for introducing order details - either by the end user or by the store owner (admin)
public class OrderCreateDto {

//    @NotNull
//    @PastOrPresent
//    private LocalDateTime orderDate;// TODOo: to be used for service function revenuePerDay

    // !! empty verification falls if the value is null
    // TODOo: the products can only be taken from the available products from db -> frontend
    @NotEmpty
    private List<@Valid @NotNull OrderItemDto> orderedProducts;

    @NotBlank
    // for the user, this is not sensitive information
    // TODOo: make it an object AddressDto
    private String deliveryAddress;
}
