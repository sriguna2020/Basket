package com.example.basket.mapper;

import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.model.BasketDto;
import com.example.basket.model.BasketPositionDto;
import com.example.basket.repository.BasketPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketMapper {

    private BasketPositionRepository basketPositionRepository;
    private BasketPositionMapper basketPositionMapper;

    @Autowired
    public BasketMapper(final BasketPositionRepository basketPositionRepository,
                        final BasketPositionMapper basketPositionMapper) {
        this.basketPositionRepository = basketPositionRepository;
        this.basketPositionMapper = basketPositionMapper;
    }

    public BasketDto map(@NotNull final Basket entity) {
        final BasketDto dto = new BasketDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        List<BasketPositionDto> basketPositionDtos = Collections.emptyList();
        if (!CollectionUtils.isEmpty(entity.getPositions())) {
            basketPositionDtos = entity.getPositions().stream().map(position -> basketPositionMapper.map(position)).collect(Collectors.toList());
        }
        dto.setBasketPositions(basketPositionDtos);

        return dto;
    }

    public Basket map(@NotNull final BasketDto dto) {
        final Basket entity = new Basket();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        List<BasketPosition> basketPositions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dto.getBasketPositions())) {
            basketPositions.addAll(
                    dto.getBasketPositions().stream().map(p -> basketPositionMapper.map(p)).collect(Collectors.toList())
            );
        }
        entity.getPositions().addAll(basketPositions);

        return entity;
    }
}

