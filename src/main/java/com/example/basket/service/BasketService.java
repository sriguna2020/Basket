package com.example.basket.service;

import com.example.basket.api.NotFoundException;
import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.entity.Product;
import com.example.basket.repository.BasketPositionRepository;
import com.example.basket.repository.BasketRepository;
import com.example.basket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final BasketPositionRepository basketPositionRepository;

    @Autowired
    public BasketService(final BasketRepository basketRepository,
                         final ProductRepository productRepository,
                         final BasketPositionRepository basketPositionRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.basketPositionRepository = basketPositionRepository;
    }

    public Basket getBasket(final Long id) throws NotFoundException {
        final Basket basket = basketRepository.findOne(id);
        if (basket == null) {
            throw new NotFoundException();
        }

        return basket;
    }

    public BigDecimal getBasketValue(final Long id) throws NotFoundException {
        final Basket basket = basketRepository.findOne(id);
        if (basket == null) {
            throw new NotFoundException();
        }

        BigDecimal value = new BigDecimal(0);
        value.setScale(2, RoundingMode.HALF_EVEN);
        for (BasketPosition basketPosition : basket.getPositions()) {
            BigDecimal productValue = productRepository.findOne(basketPosition.getProductId()).getPrice();
            productValue = productValue.setScale(2, RoundingMode.HALF_EVEN);
            productValue = productValue.multiply(new BigDecimal(basketPosition.getQuantity()));
            value = value.add(productValue);
        }

        return value;
    }

    public Basket createBasket(Basket entity) {
        return basketRepository.save(entity);
    }

    public Basket updateBasket(Basket updated) {
        Basket original = basketRepository.findOne(updated.getId());
        List<BasketPosition> newBasketPositions = new ArrayList<>();
        for (BasketPosition basketPosition : updated.getPositions()) {
            if (basketPosition.getId() == null) {
                newBasketPositions.add(basketPosition);
            } else {
                BasketPosition originalPosition = basketPositionRepository.findOne(basketPosition.getId());
                originalPosition.setQuantity(basketPosition.getQuantity());
                originalPosition.setProductId(basketPosition.getProductId());
                basketPositionRepository.save(originalPosition);
            }
        }
        original.getPositions().addAll(newBasketPositions);

        return basketRepository.save(original);
    }

    public BasketPosition findProduct(Long basketId, String phrase) throws NotFoundException {
        final Basket basket = basketRepository.findOne(basketId);
        if (basket == null) {
            throw new NotFoundException();
        }
        for (BasketPosition basketPosition : basket.getPositions()) {
            Product product = productRepository.findOne(basketPosition.getProductId());
            if (product.getName().equals(phrase)) {
                return basketPosition;
            }
        }

        return null;
    }
}