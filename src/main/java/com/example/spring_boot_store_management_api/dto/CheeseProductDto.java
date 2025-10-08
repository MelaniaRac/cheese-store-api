package com.example.spring_boot_store_management_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    // no reason to contain JPA annotations, as the DTO does not communicate w/ the db
        private Long cheeseId;
        private String cheeseName;
        private BigDecimal retailPrice;
        private int stockUnits;
        // to treat warnings related to service layer logic, IF any
        private String warningMessage;

    // "TODOo" @Positive validation for price and stockUnits
}
