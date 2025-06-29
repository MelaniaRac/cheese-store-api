package com.example.spring_boot_store_management_api.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class CheeseProductDto {

    // don't use sensitive information in the DTO

    // no reason to contain JPA annotations, as the DTO does not communicate w/ the db
        private int cheeseId;
        @NonNull private String cheeseName;
        @NonNull private BigDecimal retailPrice;
        @NonNull private int stockUnits;
}
