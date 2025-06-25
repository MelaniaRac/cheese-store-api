package com.example.spring_boot_store_management_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller // make a Java class as a Spring MVC controller
//@ResponseBody // tells the controller that the object returned is automatically serialized into JSON and passed back into the httpResponse object
@RestController
public class StoreController {

    // make this method a REST API
    // maps incoming HTTP requests to this method; handles HTTP GET request
    @GetMapping("/store")
    // for a client accessing this RESP API: http://localhost:1997/store
    public String printStore(){
        return "We are in the store (controller).";
    }

}
