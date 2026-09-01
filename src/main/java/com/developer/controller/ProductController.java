package com.developer.controller;

import com.developer.dto.ItemResponse;
import com.developer.dto.ProductRequest;
import com.developer.dto.ProductResponse;
import com.developer.service.ItemService;
import com.developer.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ItemService itemService;


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {

        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            Pageable pageable) {

        Page<ProductResponse> products =
                productService.getAllProducts(pageable);

        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {

        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponse>> getItemsByProductId(@PathVariable Integer id) {

        List<ItemResponse> items = itemService.getItemsByProductId(id);
        return ResponseEntity.ok(items);
    }
}