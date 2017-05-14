package com.example.basket.model;

import java.util.List;
import java.util.Map;

public class BasketDto {
    Long userId;
    Map<Long, Integer> products;

    public BasketDto(Long userId, Map<Long, Integer> products) {
        this.userId = userId;
        this.products = products;
    }
}
