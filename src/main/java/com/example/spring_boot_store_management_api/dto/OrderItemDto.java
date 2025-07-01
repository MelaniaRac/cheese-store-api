package com.example.spring_boot_store_management_api.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderItemDto {

    @NonNull
    private String cheeseName;
    @NonNull
    private int units;
    @NonNull
    private BigDecimal price;
}
