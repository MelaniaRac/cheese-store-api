package com.example.spring_boot_store_management_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
// for the warning message
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheeseProductDto {

    // don't use sensitive information in the DTO

    // no reason to contain JPA annotations, as the DTO does not communicate w/ the db
        private int cheeseId;
        @NonNull private String cheeseName;
        @NonNull private BigDecimal retailPrice;
        @NonNull private int stockUnits;
        // to treat warnings related to service layer logic, IF any
        private String warningMessage;
}
