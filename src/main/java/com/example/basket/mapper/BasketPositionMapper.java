package com.example.basket.mapper;

import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.model.BasketDto;
import com.example.basket.model.BasketPostionDto;
import com.example.basket.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class BasketPositionMapper {
    public BasketPosition map(@NotNull final BasketPostionDto dto) {
        final BasketPosition entity = new BasketPosition();
        entity.setBasketId(dto.getBasketId());
        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());

        return entity;
    }

    public BasketPostionDto map(@NotNull final BasketPosition entity) {
        final BasketPostionDto dto = new BasketPostionDto();
        dto.setBasketId(entity.getBasketId());
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }
}

