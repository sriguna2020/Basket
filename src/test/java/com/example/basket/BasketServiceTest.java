package com.example.basket;

import com.example.basket.api.NotFoundException;
import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.entity.Product;
import com.example.basket.repository.BasketRepository;
import com.example.basket.repository.ProductRepository;
import com.example.basket.service.BasketService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceTest {
    @Mock
    private BasketRepository basketRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private BasketService basketService;

    @Test(expected = NotFoundException.class)
    public void getBasket() throws Exception {
        // given
        // when
        basketService.getBasket(42L);
        // then
        Mockito.verify(basketRepository).findOne(42L);
    }

    @Test
    public void getBasketValue() throws Exception {
        // given
        Product product = new Product();
        product.setName("Mysz logitech");
        product.setPrice(new BigDecimal("199.28"));
        product.setId(3L);

        BasketPosition basketPosition = new BasketPosition();
        basketPosition.setProductId(3L);
        basketPosition.setQuantity(4);

        Basket basket = new Basket();
        basket.getPositions().add(basketPosition);

        Mockito.when(basketRepository.findOne(42L)).thenReturn(basket);
        Mockito.when(productRepository.findOne(3L)).thenReturn(product);
        // when
        BigDecimal value = basketService.getBasketValue(42L);
        // then
        Assertions.assertThat(value.doubleValue()).isEqualTo(797.12);
    }
}
