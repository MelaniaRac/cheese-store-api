package com.example.spring_boot_store_management_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable // to be stored in the @ElementCollection order list
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderItem {

    @NonNull
    private String cheeseName;
    @NonNull
    private int units;
    @NonNull
    private BigDecimal price;
}
