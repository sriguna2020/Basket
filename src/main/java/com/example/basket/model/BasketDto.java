package com.example.basket.model;

import com.example.basket.entity.BasketPosition;

import java.util.List;
import java.util.Map;

public class BasketDto {
    Long id;
    Long userId;
    List<BasketPosition> basketPositions;

    public BasketDto(Long userId, List<BasketDto> basketPositions) {
        this.userId = userId;
        //this.basketPositions = basketPositions;
    }

    public BasketDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}