package com.developer.service;

import com.developer.dto.ProductRequest;
import com.developer.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Integer id);

    ProductResponse updateProduct(Integer id, ProductRequest request);

    void deleteProduct(Integer id);

    Page<ProductResponse> getAllProducts(Pageable pageable);
}