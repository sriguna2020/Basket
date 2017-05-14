package com.example.basket.model;

import com.example.basket.entity.BasketPosition;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class BasketPostionDto {
    Long id;
    Long userId;
    List<BasketPosition> basketPositions;

    public BasketPostionDto(Long userId, List<BasketPostionDto> basketPositions) {
        this.userId = userId;
        //this.basketPositions = basketPositions;
    }

    public BasketPostionDto() {
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

//    public Map<Long, Integer> getBasketPositions() {
//        return basketPositions;
//    }
//
//    public void setBasketPositions(Map<Long, Integer> basketPositions) {
//        this.basketPositions = basketPositions;
//    }
}
