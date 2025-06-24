package com.example.spring_boot_store_managament_api.bean;

import lombok.*;

//@Getter
//@Setter
//@EqualsAndHashCode
@NoArgsConstructor(force = true)
// @RequiredArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
// despite @Data, kept some of the above annotations for my particular examples, otherwise error on constructor because Data generates only the Requested constructor
@Data

@Builder
//@ToString
public class CheeseProduct {

    // getter/setter for a certain field
    // by default, lombok generates the getter/setter with 'public' modifier
//    @Getter(AccessLevel.PRIVATE)
//    @Setter(AccessLevel.PROTECTED)
    //@EqualsAndHashCode.Exclude()
    //@NonNull
    private final String cheese;
    // @ToString.Exclude
    private int price;

    public static void main(String[] args) {
        CheeseProduct cheeseProduct = new CheeseProduct.CheeseProductBuilder()
                .cheese("Chavignol")
                .price(24)
                .build();

        System.out.println(cheeseProduct);
    }

}
