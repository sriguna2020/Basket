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
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

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

    @Test
    public void testCreateBasket() {
        // given
        final Basket basket = Mockito.mock(Basket.class);
        // when
        basketService.createBasket(basket);
        // then
        Mockito.verify(basketRepository).save(basket);
    }

    @Test
    public void testSaveWithDuplicateRemoval() {
        Product product = new Product();
        product.setName("Mysz logitech");
        product.setPrice(new BigDecimal("199.28"));
        product.setId(3L);

        BasketPosition basketPosition = new BasketPosition();
        basketPosition.setProductId(3L);
        basketPosition.setQuantity(4);

        BasketPosition basketPosition1 = new BasketPosition();
        basketPosition1.setProductId(3L);
        basketPosition1.setQuantity(4);

        Basket basket = new Basket();
        basket.getPositions().addAll(Arrays.asList(basketPosition, basketPosition1));

        Mockito.when(basketRepository.findOne(42L)).thenReturn(basket);
        Mockito.when(productRepository.findOne(3L)).thenReturn(product);
        Mockito.when(basketRepository.save(Mockito.any(Basket.class))).then(AdditionalAnswers.returnsFirstArg());
        // when
        Basket updatedBasket = basketService.saveWithDuplicateRemoval(basket);
        // then
        Assertions.assertThat(updatedBasket.getPositions().size()).isEqualTo(1);
    }
}
