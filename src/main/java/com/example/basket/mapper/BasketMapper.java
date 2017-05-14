package com.example.basket.mapper;

import com.example.basket.entity.Basket;
import com.example.basket.model.BasketDto;
import com.example.basket.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class BasketMapper {
    private final BasketRepository basketRepository;

    @Autowired
    public BasketMapper(final BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket map(@NotNull final BasketDto dto) {
        final Basket entity = new Basket();
        //entity.setProducts(dto.getProducts());
        entity.setId(dto.getUserId());
        entity.setId(dto.getId());

        return entity;
    }

    public BasketDto map(@NotNull final Basket entity) {
        final BasketDto dto = new BasketDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        //dto.setProducts(entity.getProducts());

        return dto;
    }
}

