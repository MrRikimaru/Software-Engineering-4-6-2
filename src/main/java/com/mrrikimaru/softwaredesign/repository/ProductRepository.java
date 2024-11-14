package com.mrrikimaru.softwaredesign.repository;

import com.mrrikimaru.softwaredesign.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String apiece);
}