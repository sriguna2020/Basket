package com.example.basket.service;

import com.example.basket.api.NotFoundException;
import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.repository.BasketRepository;
import com.example.basket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BasketService {

    @Autowired
    private final BasketRepository basketRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    public BasketService(final BasketRepository basketRepository,
                         final ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
    }

    public Basket getBasket(final Long id) throws NotFoundException {
        final Basket basket = basketRepository.findOne(id);
        if (basket == null) {
            throw new NotFoundException();
        }

        return basket;
    }

    public Long getBasketValue(final Long id) throws NotFoundException {
        final Basket basket = basketRepository.findOne(id);
        if (basket == null) {
            throw new NotFoundException();
        }

        Long value = 0L;
        for (BasketPosition basketPosition : basket.getPositions()) {
            Long productValue = productRepository.findOne(basketPosition.getProductId()).getPrice();
            value += productValue * basketPosition.getQuantity();
        }

        return value;
    }
}