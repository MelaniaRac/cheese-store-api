package com.example.spring_boot_store_management_api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor

public class OrderItemDto {

    @NotBlank
    private String cheeseName;
    @Positive
    private int orderedUnits;

    // for security reasons, the price should not be editable by the user
    // the user should only be able to select the product
}
