package com.example.spring_boot_store_management_api.controller;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
// define base URL for defining the other REST APIs
@RequestMapping("cheese")
public class CheeseController {

    @GetMapping//("cheese")
    // returns a CheeseProduct object in JSON format to the client
//    public CheeseProduct getCheeseSelection(){
      public ResponseEntity<CheeseProduct> getCheeseProduct(){
        CheeseProduct cheese = new CheeseProduct("Burduf in coaja de brad", new BigDecimal("39.5"), 1);
        // configuring body of the response + the HTTP code
        // return new ResponseEntity<>(new CheeseProduct("Burduf in coaja de brad", 39), HttpStatus.OK);
        // same as
        // return ResponseEntity.ok(new CheeseProduct("Burduf in coaja de brad", 39));
        return ResponseEntity.ok().header("custom-header", "cheese store custom").body(cheese);
    }

    @GetMapping("/cheese-list")
    // returns a list of CheeseProduct object in JSON format to the client
    public ResponseEntity<List<CheeseProduct>> getCheeseList(){
        List<CheeseProduct> cheeseList = new ArrayList<>();
        cheeseList.add(new CheeseProduct("Saint-felicien", new BigDecimal("40"), 2));
        cheeseList.add(new CheeseProduct("Rocamadour", new BigDecimal("24.7"), 3));
        cheeseList.add(new CheeseProduct("Cas vaca sarat", new BigDecimal("35.0"), 5));

        return ResponseEntity.ok(cheeseList);
    }

    // spring boot REST API with Path Variable
    // {name} = URI template variable -> http://localhost:2028/cheese/branzaTest
    @GetMapping("{name}") // {name}/{secondCheeseName}
    // PathVariable for binding the URI template to the cheeseName parameter
    public ResponseEntity<CheeseProduct> cheesePathVariable(@PathVariable("name") String cheeseName){

        return new ResponseEntity<>(new CheeseProduct(cheeseName, new BigDecimal("45.5"), 4), HttpStatus.OK);
    }

    // spring boot REST API with request parameters
    // (id=1 query parameter)
    // http://localhost:2028/cheese/query?cheeseNameRequested=branzaTest&cheesePrice=28
    @GetMapping("query")
    public ResponseEntity<CheeseProduct> cheeseRequestVariable(@RequestParam int cheeseId, @RequestParam String cheeseNameRequested){
        CheeseProduct queryCheese = new CheeseProduct(cheeseNameRequested, new BigDecimal("25.5"), 4);

        return ResponseEntity.ok(queryCheese);
    }

    // Spring Boot REST API that handles HTTP POST request (create new response)
    // maps HTTP POST request onto specific handler methods
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    // RequestBody is responsible for retrieving the HTTP request body and automatically convert the JSON to the Java object
    public ResponseEntity<CheeseProduct> createCheese(@RequestBody CheeseProduct cheese){
        System.out.println("Name of the new product: " + cheese.getCheeseName());
        System.out.println("Price: " + cheese.getPrice());

        // we are not using "ok" method because it implies a 200 code, while the HttpStatus.CREATED is 201
        return new ResponseEntity<>(cheese, HttpStatus.CREATED);
    }

    // Spring Boot REST API that handles HTTP PUT request (update existing resource)
    // maps incoming HTTP PUT request to this method
    @PutMapping("{cheeseName}/update")
    public ResponseEntity<CheeseProduct> updateCheese(@RequestBody CheeseProduct cheese, @PathVariable String cheeseName){;
        System.out.println(cheese.getPrice());
        // @PathVariable does not automatically populate your @RequestBody object’s fields
        // => the name of the cheese returned will be null
        return ResponseEntity.ok(cheese);
    }

    // Spring Boot REST API that handles HTTP DELETE request
    // maps DELETE request
    @DeleteMapping("{name}/delete")
    public ResponseEntity<String> deleteCheese(@PathVariable("name") String cheeseName){
        return ResponseEntity.ok("Cheese product deleted successfully.");
    }
}
