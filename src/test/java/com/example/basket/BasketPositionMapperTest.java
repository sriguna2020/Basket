package com.example.basket;

import com.example.basket.entity.BasketPosition;
import com.example.basket.mapper.BasketPositionMapper;
import com.example.basket.model.BasketPositionDto;
import com.example.basket.repository.BasketPositionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasketPositionMapperTest {
    @Mock
    private BasketPositionRepository basketPositionRepository;

    @InjectMocks
    private BasketPositionMapper basketPositionMapper;

    @Test
    public void testMapBasketPositionDto() throws Exception {
        // given
        BasketPositionDto dto = new BasketPositionDto();
        dto.setQuantity(12);
        dto.setProductId(4L);
        // when
        BasketPosition entity = basketPositionMapper.map(dto);
        // then
        Assertions.assertThat(entity.getId()).isEqualTo(dto.getId());
        Assertions.assertThat(entity.getQuantity()).isEqualTo(dto.getQuantity());
        Assertions.assertThat(entity.getProductId()).isEqualTo(dto.getProductId());
    }

    @Test
    public void testMapBasketPosition() throws Exception {
        BasketPosition entity = new BasketPosition();
        entity.setQuantity(12);
        entity.setProductId(4L);
        // when
        BasketPositionDto dto = basketPositionMapper.map(entity);
        // then
        Assertions.assertThat(entity.getId()).isEqualTo(dto.getId());
        Assertions.assertThat(entity.getQuantity()).isEqualTo(dto.getQuantity());
        Assertions.assertThat(entity.getProductId()).isEqualTo(dto.getProductId());
    }
}
