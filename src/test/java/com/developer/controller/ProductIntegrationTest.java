package com.developer.controller;

import com.developer.entity.Product;
import com.developer.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void applicationContext_shouldLoad() {

        assertNotNull(productRepository);
    }

    @Test
    void productRepository_shouldSaveProduct() {

        Product product = Product.builder()
                .productName("Integration Laptop")
                .createdBy("admin")
                .createdOn(LocalDateTime.now())
                .build();

        Product savedProduct =
                productRepository.save(product);

        assertNotNull(savedProduct.getId());

        assertEquals(
                "Integration Laptop",
                savedProduct.getProductName()
        );
    }
}