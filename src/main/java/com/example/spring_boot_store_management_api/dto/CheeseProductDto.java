package com.example.spring_boot_store_management_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

// TODOo: instead using Lombok, could make it a record
@Setter
@Getter
@NoArgsConstructor
//@RequiredArgsConstructor
// for the warning message
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheeseProductDto {

    // no sensitive information in the DTO
// TODOo: why difference between name null => 400 bad request , price null => 500 internal server error, stock null => ok (it's translated automatically to 0)
        private Long cheeseId;
        @NotBlank(message = "Cheese name must not be empty.")

        private String cheeseName;
        // TODOo: create handlers for the below error messages (AC: from a legal pov, the discount cannot be 100%)
        @Positive(message = "Retail price must be positive.")
        @DecimalMin(value = "0.01", message = "Price must be at least 0,01 RON.")
        private BigDecimal retailPrice;

        // stock units can be zero because the owner wants to see them in the database to know what to restock
        // the products that are out of stock should not be deleted from the database
        @PositiveOrZero(message = "Stock units must be positive whole numbers or zero.")
        private Integer stockUnits;

        private String lowStockWarning;
}
