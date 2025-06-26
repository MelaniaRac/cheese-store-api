package com.example.spring_boot_store_management_api.repository;

import com.example.spring_boot_store_management_api.entity.CheeseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository not needed because JpaRepository already contains this annotation
// by simply extending, our class gets all methods from JpaRepository
public interface CheeseProductRepository extends JpaRepository<CheeseProduct, Integer> {


}
