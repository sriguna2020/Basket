package com.example.basket.model;

import java.util.List;

public class BasketDto {
    Long id;
    Long userId;
    List<Long> basketPositionsIds;

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

    public List<Long> getBasketPositionsIds() {
        return basketPositionsIds;
    }

    public void setBasketPositionsIds(List<Long> basketPositionsIds) {
        this.basketPositionsIds = basketPositionsIds;
    }
}
