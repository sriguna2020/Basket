package com.example.basket.model;

import com.example.basket.entity.BasketPosition;

import java.util.List;

public class ProductDto {
    private Long id;
    private String name;

    // todo: bigdecimal
    private Long price;

    public ProductDto(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}