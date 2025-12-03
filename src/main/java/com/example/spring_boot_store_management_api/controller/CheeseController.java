package com.example.spring_boot_store_management_api.controller;

import com.example.spring_boot_store_management_api.dto.CheeseProductDto;
import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import com.example.spring_boot_store_management_api.repository.CheeseProductRepository;
import com.example.spring_boot_store_management_api.service.CheeseProductService;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
// define base URL for defining the other REST APIs
@RequestMapping("cheeses")
@AllArgsConstructor
public class CheeseController {

    private CheeseProductService cheeseProductService;

//    http://localhost:2028/cheese/create
    // build create cheese product REST API
    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CheeseProductDto> createCheeseProduct(@Valid @RequestBody CheeseProductDto cheeseProductDto){
        CheeseProductDto productAdded = cheeseProductService.createCheeseProduct(cheeseProductDto);

        return new ResponseEntity<>(productAdded, HttpStatus.CREATED);
    }

//    http://localhost:2028/cheese/Chabichou
    // build get product by cheeseName
    @GetMapping("{name}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CheeseProductDto> findCheese(@PathVariable("name") String cheeseName){
        CheeseProductDto productSearchedDto = cheeseProductService.findCheese(cheeseName);

        return new ResponseEntity<>(productSearchedDto, HttpStatus.OK);
    }


//     http//localhost:2028/cheese/stockUnits/retailPrice
    // build get products by stock units and price REST API
    // TODOo : make the URL using query parameters
    @GetMapping("{stockUnits}/{retailPrice}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CheeseProductDto>> findByStockUnitsAndRetailPriceLessThan(Integer stockUnits,
                                                                                      BigDecimal retailPrice){
        List<CheeseProductDto> productsSearched= cheeseProductService.findByStockUnitsAndRetailPriceLessThan((Integer) stockUnits, (BigDecimal) retailPrice);

        return new ResponseEntity<>(productsSearched, HttpStatus.OK);
    }

    //    http://localhost:2028/cheese
    // update one or more fields for cheeseProduct
    @PatchMapping("partialUpdate/{productName}")
    @PreAuthorize("hasRole('ADMIN')")
    // eliminated cheeseName path variable to not risk confusion if the following corner case applies:
    // the user could write name=A in the http request and name=b in the JSON body
    public ResponseEntity<CheeseProductDto> patchCheeseProduct(@Valid @PathVariable("productName") String cheeseName,
                                                                   @RequestBody CheeseProductDto productDto){

        CheeseProductDto productFieldsUpdated = cheeseProductService.patchProduct(cheeseName, productDto);

        return new ResponseEntity<>(productFieldsUpdated, HttpStatus.OK);
    }


    // build delete product REST API
    @DeleteMapping("{stockUnits}")
    @PreAuthorize("hasRole('ADMIN')")
    // TODOo shouldn't the REST endpoint be with query?
    // TODOo validation
    // TODOo the end users should still have access to the possible products - grey them out with "out of stock"
    // AC: store owner wants to be able to delete
    public ResponseEntity<String> deleteByStockUnits(@PathVariable("stockUnits") Integer stockUnits){
        long numberDeletedProducts = cheeseProductService.deleteByStockUnits(stockUnits);

        return new ResponseEntity<>("Products out of stock were deleted:", HttpStatus.OK);
    }

    // custom exceptions related to the controller can also be handled inside the controller layer
    // ex: different error formats can make it easier to localize the logic.
}
