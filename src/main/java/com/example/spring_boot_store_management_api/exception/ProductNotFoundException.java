package com.example.spring_boot_store_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// to modify status from the Postman client
// custom exception handled with default Spring Boot default error handling
//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName; // resource element of interest to the user
    //private String fieldValue; // the value of the resource element

    // less detail in the error message for security reasons: eliminated fieldValue
    // TODOo:
    public ProductNotFoundException(String resourceName, String fieldName){
        super(String.format("%s not found with given %s.", resourceName, fieldName));
    }
}
