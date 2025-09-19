package com.example.spring_boot_store_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OutOfStockException extends RuntimeException{

    // less detail in the error message for security reasons: eliminated fieldValue
    public OutOfStockException(String resourceName){
        super(String.format("Requested units exceed available stock for %s", resourceName));
    }
}
