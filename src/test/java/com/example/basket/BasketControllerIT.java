package com.example.basket;

import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.model.BasketDto;
import com.example.basket.repository.BasketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BasketApplication.class)
@WebAppConfiguration
@Transactional
public class BasketControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BasketRepository basketRepository;

    protected MockMvc mockMvc;

    protected final ObjectMapper mapper = new ObjectMapper();

    private Long basketId;

    @Before
    public void setup() {
        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
        //mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        Basket basket = new Basket();
        basket.setBasketPositions(Collections.emptyList());
        basket.setUserId(1L);
        //basket.setProducts(Collections.emptyMap());
        basketRepository.save(basket);
        basketId = basket.getId();
    }

    @Test
    public void testGetBasket() throws Exception {
        // given
        final BasketDto dto = createBasketDto();
        // when
        final ResultActions request = mockMvc.perform(get("/basket/" + basketId));
        final MockHttpServletResponse response = request.andReturn().getResponse();
        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getHeader("Location")).isNotEmpty().matches("https?://.*/addons/-?\\d+");
    }

    private BasketDto createBasketDto() {
        return new BasketDto(2L, Collections.emptyList());
    }
}
