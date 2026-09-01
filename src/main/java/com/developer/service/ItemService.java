package com.developer.service;

import com.developer.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    List<ItemResponse> getItemsByProductId(Integer productId);
}