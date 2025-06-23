package com.example.spring_boot_store_managament_api.bean;

public class CheeseProduct {

    private String cheese;
    private int price;

    public CheeseProduct(String cheese, int price) {
        this.cheese = cheese;
        this.price = price;
    }

    public String getCheeseName() {
        return cheese;
    }

    public void setCheeseName(String cheese) { this.cheese = cheese;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
