package com.example.spring_boot_store_management_api.exception;

public class InvalidUpdateException extends Throwable {
    public InvalidUpdateException(String errorMessage) {
        super(errorMessage);
    }
}
