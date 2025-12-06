package com.example.spring_boot_store_management_api.repository;

import com.example.spring_boot_store_management_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // to compute the "total revenue per day" business logic
    public List<Order> findByOrderDate(LocalDate orderDate);
}
