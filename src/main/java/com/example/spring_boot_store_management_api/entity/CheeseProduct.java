package com.example.spring_boot_store_management_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

//@Getter
//@Setter
//@EqualsAndHashCode
@NoArgsConstructor()
@RequiredArgsConstructor
//@AllArgsConstructor
@ToString(includeFieldNames = false)
@Data

// specify this class has a JPA entity
@Entity
@Table(name="cheese_products",
       uniqueConstraints = {
        //  the db itself will reject any INSERT or UPDATE that would cause two rows to share the same cheese_name
            @UniqueConstraint(
                    name = "unique cheese name", // the db's constraint name
                    columnNames = "cheese_name") // column on which uniqueness in enforced
            // we can have a list pf unique constraints here
       })

public class CheeseProduct {
    //@NonNull
    @Id
    @Column(nullable = false)
    //@Column(name = "nameYouWant", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY strategy relies on the db auto-increment column
    private int cheeseId;

    @NonNull
    @Column(nullable = false)
    private String cheeseName; // String not allowed for auto_increment in MySQL => cannot use INCREMENT

    @NonNull
    @Column(nullable = false)
    private BigDecimal price;

    @NonNull
    private int stockUnits;

}
