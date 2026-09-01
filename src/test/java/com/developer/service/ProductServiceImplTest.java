package com.developer.service;

import com.developer.dto.ProductRequest;
import com.developer.dto.ProductResponse;
import com.developer.entity.Product;
import com.developer.exception.ResourceNotFoundException;
import com.developer.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest request;

    @BeforeEach
    void setUp() {

        product = Product.builder()
                .id(1)
                .productName("Laptop")
                .createdBy("admin")
                .build();

        request = new ProductRequest();
        request.setProductName("Laptop");
    }


    // =========================
    // CREATE PRODUCT
    // =========================

    @Test
    void createProduct_shouldCreateProduct() {

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse response =
                productService.createProduct(request);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Laptop", response.getProductName());

        verify(productRepository, times(1))
                .save(any(Product.class));
    }


    // =========================
    // GET ALL PRODUCTS
    // =========================

    @Test
    void getAllProducts_shouldReturnProducts() {

        PageRequest pageable = PageRequest.of(0, 10);

        Page<Product> page =
                new PageImpl<>(List.of(product));

        when(productRepository.findAll(pageable))
                .thenReturn(page);

        Page<ProductResponse> response =
                productService.getAllProducts(pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("Laptop",
                response.getContent().get(0).getProductName());

        verify(productRepository, times(1))
                .findAll(pageable);
    }


    // =========================
    // GET PRODUCT BY ID
    // =========================

    @Test
    void getProductById_shouldReturnProduct() {

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                productService.getProductById(1);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Laptop", response.getProductName());

        verify(productRepository, times(1))
                .findById(1);
    }


    // =========================
    // GET PRODUCT BY ID - NOT FOUND
    // =========================

    @Test
    void getProductById_shouldThrowExceptionWhenNotFound() {

        when(productRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(99)
        );

        verify(productRepository, times(1))
                .findById(99);
    }


    // =========================
    // UPDATE PRODUCT
    // =========================

    @Test
    void updateProduct_shouldUpdateProduct() {

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse response =
                productService.updateProduct(1, request);

        assertNotNull(response);
        assertEquals("Laptop", response.getProductName());

        verify(productRepository, times(1))
                .findById(1);

        verify(productRepository, times(1))
                .save(product);
    }


    // =========================
    // DELETE PRODUCT
    // =========================

    @Test
    void deleteProduct_shouldDeleteProduct() {

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1);

        verify(productRepository, times(1))
                .findById(1);

        verify(productRepository, times(1))
                .delete(product);
    }


    // =========================
    // DELETE PRODUCT - NOT FOUND
    // =========================

    @Test
    void deleteProduct_shouldThrowExceptionWhenNotFound() {

        when(productRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteProduct(99)
        );

        verify(productRepository, times(1))
                .findById(99);

        verify(productRepository, never())
                .delete(any(Product.class));
    }
}