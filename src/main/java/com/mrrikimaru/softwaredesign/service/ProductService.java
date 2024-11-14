package com.mrrikimaru.softwaredesign.service;

import com.mrrikimaru.softwaredesign.model.Product;
import com.mrrikimaru.softwaredesign.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategoryApiece() {
        return productRepository.findByCategory("apiece");
    }

    public List<Product> getProductsByCategoryComposition() {
        return productRepository.findByCategory("composition");
    }

    public List<Product> getProductsByCategoryBouquets() {
        return productRepository.findByCategory("bouquets");
    }

    public List<Product> getProductsByCategoryRoses() {
        return productRepository.findByCategory("roses");
    }

    public List<Product> getProductsByCategoryToys() {
        return productRepository.findByCategory("toys");
    }

    public List<Product> getProductsByCategoryBalloons() {
        return productRepository.findByCategory("balloons");
    }

    public List<Product> getProductsByCategoryPresent() {
        return productRepository.findByCategory("present");
    }

    public List<Product> getProductsByCategoryPostcards() {
        return productRepository.findByCategory("postcards");
    }
}
