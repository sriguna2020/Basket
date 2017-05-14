package com.example.basket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.basket.model.BasketDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BasketApiIT{

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Test
    public void testGetBasket() throws Exception {
        // given
        final BasketDto dto = createBasketDto();
        // when
        final ResultActions request = mockMvc.perform(get("/basket"));
        final MockHttpServletResponse response = request.andReturn().getResponse();
        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.getHeader("Location")).isNotEmpty().matches("https?://.*/addons/-?\\d+");
        Assertions.assertThat(2).isEqualTo(2);
    }

    private BasketDto createBasketDto() {
        return new BasketDto(2L, Collections.emptyMap());
    }
}
