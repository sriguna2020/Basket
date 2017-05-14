package com.example.basket.mapper;

import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.model.BasketDto;
import com.example.basket.repository.BasketPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Service
public class BasketMapper {

    private BasketPositionRepository basketPositionRepository;

    @Autowired
    public BasketMapper(final BasketPositionRepository basketPositionRepository) {
        this.basketPositionRepository = basketPositionRepository;
    }

    public Basket map(@NotNull final BasketDto dto) {
        final Basket entity = new Basket();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setBasketPositions(dto.getBasketPositionsIds().stream().map(
                id -> basketPositionRepository.findOne(id)).collect(Collectors.toList()));

        return entity;
    }

    public BasketDto map(@NotNull final Basket entity) {
        final BasketDto dto = new BasketDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setBasketPositionsIds(entity.getPositions().stream().map(
                BasketPosition::getId).collect(Collectors.toList()));

        return dto;
    }
}

