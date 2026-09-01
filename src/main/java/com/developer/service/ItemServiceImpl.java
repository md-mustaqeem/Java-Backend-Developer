package com.developer.service;

import com.developer.dto.ItemResponse;
import com.developer.entity.Item;
import com.developer.exception.ResourceNotFoundException;
import com.developer.repository.ItemRepository;
import com.developer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ItemResponse> getItemsByProductId(Integer productId) {

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        List<Item> items = itemRepository.findByProductId(productId);

        return items.stream()
                .map(item -> ItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .build())
                .toList();
    }
}
