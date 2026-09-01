package com.developer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    private Integer id;

    private Integer productId;

    private Integer quantity;
}
