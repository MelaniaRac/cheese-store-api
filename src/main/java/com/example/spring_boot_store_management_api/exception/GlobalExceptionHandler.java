package com.example.spring_boot_store_management_api.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// for handling exceptions globally
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler methods are called by Spring via reflection
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFoundException(ProductNotFoundException exception, WebRequest webRequest){

        // body of the error
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                //webRequest.getDescription(false),
                "PRODUCT_NOT_FOUND"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorDetails> handleOutOfStockException(OutOfStockException exception, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                //webRequest.getDescription(false),
                // TODOo: check difference between this design and the below one
                "ORDERED_UNITS_EXCEED_STOCK"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // check if input values mismatch the expected type
    // TODOo works only for retailPrice
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInputMappingException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        // Try to determine the exact field that caused the issue from the Jackson exception stack
        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException mappingException = (JsonMappingException) ex.getCause();

            // Extract the field name from the exception path reference
            String fieldName = mappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            // Provide a specific error message for that field
            errors.put(fieldName, "Invalid input format or type mismatch.");

        } else {
            // Fallback for general unreadable messages if field extraction fails
            errors.put("general", "Malformed JSON request. Check data types.");
        }

        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    }
