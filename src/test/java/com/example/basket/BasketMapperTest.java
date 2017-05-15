package com.example.basket;

import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.mapper.BasketMapper;
import com.example.basket.mapper.BasketPositionMapper;
import com.example.basket.model.BasketDto;
import com.example.basket.model.BasketPositionDto;
import com.example.basket.repository.BasketPositionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class BasketMapperTest {
    @Mock
    private BasketPositionRepository basketPositionRepository;

    @Mock
    private BasketPositionMapper basketPositionMapper;

    @InjectMocks
    private BasketMapper basketMapper;

    @Test
    public void testMapBasketDto() throws Exception {
        // given
        BasketPositionDto basketPositionDto = new BasketPositionDto();
        basketPositionDto.setProductId(1L);
        basketPositionDto.setQuantity(44);

        BasketPosition basketPosition = new BasketPosition();
        basketPosition.setProductId(1L);
        basketPosition.setQuantity(44);

        BasketDto dto = new BasketDto();
        dto.setId(42L);
        dto.setBasketPositions(Collections.singletonList(basketPositionDto));
        dto.setUserId(12L);
        Mockito.when(basketPositionMapper.map(basketPositionDto)).thenReturn(basketPosition);
        // when
        Basket entity = basketMapper.map(dto);
        // then
        Assertions.assertThat(entity.getId()).isEqualTo(dto.getId());
        Assertions.assertThat(entity.getUserId()).isEqualTo(dto.getUserId());
        Assertions.assertThat(entity.getPositions().size()).isEqualTo(1);
        Assertions.assertThat(entity.getPositions().get(0).getBasketId())
                .isEqualTo(dto.getBasketPositions().get(0).getBasketId());
        Assertions.assertThat(entity.getPositions().get(0).getId())
                .isEqualTo(dto.getBasketPositions().get(0).getId());
        Assertions.assertThat(entity.getPositions().get(0).getProductId())
                .isEqualTo(dto.getBasketPositions().get(0).getProductId());
        Assertions.assertThat(entity.getPositions().get(0).getQuantity())
                .isEqualTo(dto.getBasketPositions().get(0).getQuantity());
    }

    @Test
    public void testMapBasket() throws Exception {
        BasketPositionDto basketPositionDto = new BasketPositionDto();
        basketPositionDto.setProductId(1L);
        basketPositionDto.setQuantity(44);

        BasketPosition basketPosition = new BasketPosition();
        basketPosition.setProductId(1L);
        basketPosition.setQuantity(44);

        Basket entity = new Basket();
        entity.setId(42L);
        entity.getPositions().add(basketPosition);
        entity.setUserId(12L);
        Mockito.when(basketPositionMapper.map(basketPosition)).thenReturn(basketPositionDto);
        // when
        BasketDto dto = basketMapper.map(entity);
        // then
        Assertions.assertThat(entity.getId()).isEqualTo(dto.getId());
        Assertions.assertThat(entity.getUserId()).isEqualTo(dto.getUserId());
        Assertions.assertThat(entity.getPositions().size()).isEqualTo(1);
        Assertions.assertThat(entity.getPositions().get(0).getBasketId())
                .isEqualTo(dto.getBasketPositions().get(0).getBasketId());
        Assertions.assertThat(entity.getPositions().get(0).getId())
                .isEqualTo(dto.getBasketPositions().get(0).getId());
        Assertions.assertThat(entity.getPositions().get(0).getProductId())
                .isEqualTo(dto.getBasketPositions().get(0).getProductId());
        Assertions.assertThat(entity.getPositions().get(0).getQuantity())
                .isEqualTo(dto.getBasketPositions().get(0).getQuantity());
    }
}
