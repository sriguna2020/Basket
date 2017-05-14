package com.example.basket.service;

import com.example.basket.entity.Basket;
import com.example.basket.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BasketService {

    @Autowired
    private final BasketRepository basketRepository;

    @Autowired
    public BasketService(final BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket getBasket(final Long id) {
        return basketRepository.findOne(id);
    }
}