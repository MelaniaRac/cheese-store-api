package com.example.spring_boot_store_management_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

// instead of a class using Lombok, could have made it a record (check advantages first)
@Setter
@Getter
@NoArgsConstructor
//@RequiredArgsConstructor
// for the warning message
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheeseProductDto {

    // don't use sensitive information in the DTO
// TODOo: name null => 400 bad request , price null => 500 error, stock null => ok (it's translated automatically to 0)
    // no reason to contain JPA annotations, as the DTO does not communicate w/ the db
        private Long cheeseId;
        @NotBlank(message = "Cheese name must not be empty.")

        private String cheeseName;
        // TODOo: create handlers for the below error messages (AC: from a legal pov, the discount cannot be 100%)
        @Positive(message = "Retail price must be positive.")
        @DecimalMin(value = "0.01", message = "Price must be at least 0,01 RON.")
        private BigDecimal retailPrice;
        // TODOo: create handlers for errors
        // stock units can be zero because the owner wants to see them in the database to know what to restock
        @PositiveOrZero(message = "Stock units must be positive whole numbers or zero.")
        private Integer stockUnits;
        // to treat warnings related to service layer logic, IF any
    // TODOo: move it someplace else? it's not a cheese product related field
        private String warningMessage;

    // "TODOo" @Positive validation for price and stockUnits
}
