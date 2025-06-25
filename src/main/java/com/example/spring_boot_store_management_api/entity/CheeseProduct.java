package com.example.spring_boot_store_management_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

//@Getter
//@Setter
//@EqualsAndHashCode
@NoArgsConstructor(force = true)
// @RequiredArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
// despite @Data, kept some of the above annotations for my particular examples, otherwise error on constructor because Data generates only the Required constructor
@Data

// specify this class has a JPA entity
@Entity
@Table(name="cheese-products")
public class CheeseProduct {

    // getter/setter for a certain field
    // by default, lombok generates the getter/setter with 'public' modifier
//    @Getter(AccessLevel.PRIVATE)
//    @Setter(AccessLevel.PROTECTED)


    //@EqualsAndHashCode.Exclude()
    //@NonNull
    @Id
    private final String cheeseName;
    // @ToString.Exclude
    private final int price;


}
