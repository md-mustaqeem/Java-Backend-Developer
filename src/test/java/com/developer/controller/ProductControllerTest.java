package com.developer.controller;

import com.developer.dto.ProductRequest;
import com.developer.dto.ProductResponse;
import com.developer.service.ItemService;
import com.developer.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ProductController productController;


    @Test
    void createProduct_shouldReturnCreated() {

        ProductRequest request = new ProductRequest();
        request.setProductName("Laptop");

        ProductResponse product = ProductResponse.builder()
                .id(1)
                .productName("Laptop")
                .build();

        when(productService.createProduct(request))
                .thenReturn(product);

        ResponseEntity<ProductResponse> response =
                productController.createProduct(request);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Laptop",
                response.getBody().getProductName());

        verify(productService).createProduct(request);
    }


    @Test
    void getProductById_shouldReturnOk() {

        ProductResponse product = ProductResponse.builder()
                .id(1)
                .productName("Laptop")
                .build();

        when(productService.getProductById(1))
                .thenReturn(product);

        ResponseEntity<ProductResponse> response =
                productController.getProductById(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Laptop",
                response.getBody().getProductName());

        verify(productService).getProductById(1);
    }


    @Test
    void updateProduct_shouldReturnOk() {

        ProductRequest request = new ProductRequest();
        request.setProductName("Updated Laptop");

        ProductResponse product = ProductResponse.builder()
                .id(1)
                .productName("Updated Laptop")
                .build();

        when(productService.updateProduct(1, request))
                .thenReturn(product);

        ResponseEntity<ProductResponse> response =
                productController.updateProduct(1, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated Laptop",
                response.getBody().getProductName());

        verify(productService)
                .updateProduct(1, request);
    }


    @Test
    void deleteProduct_shouldReturnNoContent() {

        doNothing()
                .when(productService)
                .deleteProduct(1);

        ResponseEntity<Void> response =
                productController.deleteProduct(1);

        assertEquals(204, response.getStatusCode().value());

        verify(productService)
                .deleteProduct(1);
    }


    @Test
    void getItemsByProductId_shouldReturnOk() {

        when(itemService.getItemsByProductId(1))
                .thenReturn(List.of());

        ResponseEntity<?> response =
                productController.getItemsByProductId(1);

        assertEquals(200, response.getStatusCode().value());

        verify(itemService)
                .getItemsByProductId(1);
    }
}