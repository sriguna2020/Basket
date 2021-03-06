package com.example.basket.mapper;

import com.example.basket.entity.BasketPosition;
import com.example.basket.model.BasketPositionDto;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class BasketPositionMapper {
    public BasketPosition map(@NotNull final BasketPositionDto dto) {
        final BasketPosition entity = new BasketPosition();
        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());

        return entity;
    }

    public BasketPositionDto map(@NotNull final BasketPosition entity) {
        final BasketPositionDto dto = new BasketPositionDto();
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }
}

