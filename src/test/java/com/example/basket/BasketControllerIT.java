package com.example.basket;

import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketPosition;
import com.example.basket.entity.Product;
import com.example.basket.model.BasketDto;
import com.example.basket.model.BasketPositionDto;
import com.example.basket.repository.BasketRepository;
import com.example.basket.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BasketApplication.class)
@WebAppConfiguration
@Transactional
public class BasketControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    // Hardcoded
    private static final Long USER_ID = 1L;

    private Long basketId;
    private Long productId;
    private Long productId1;
    private Long productId2;

    @Before
    public void setup() {
        // UserID - hardcoded

        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
        //mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        Basket basket = new Basket();
        basket.setBasketPositions(Collections.emptyList());
        basket.setUserId(1L);
        basketRepository.save(basket);
        basketId = basket.getId();

        productId = createProduct("Intel 4670k", new BigDecimal(899.87));
        productId1 = createProduct("Burbulator", new BigDecimal(9000.01));
        productId2 = createProduct("Przystawka do burbulatora", new BigDecimal(0.05));
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
    }

    @Test
    public void testCreateEmptyBasket() throws Exception {
        // given
        final BasketDto dto = createBasketDto();
        // when
        final ResultActions request = mockMvc.perform(fileUpload("/basket").file(createDtoUpload(dto)));
        final MockHttpServletResponse response = request.andReturn().getResponse();
        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.getHeader("Location")).isNotEmpty().matches("https?://.*/basket/-?\\d+");
    }

    @Test
    public void testCreateBasketWithPositions() throws Exception {
        // given
        final BasketDto basketDto = createBasketDtoWithPositions();
        // when
        final ResultActions request = mockMvc.perform(fileUpload("/basket").file(createDtoUpload(basketDto)));
        final MockHttpServletResponse response = request.andReturn().getResponse();
        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.getHeader("Location")).isNotEmpty().matches("https?://.*/basket/-?\\d+");
    }

    @Test
    public void getBasketValue() throws Exception {
        // given
        final BasketDto basketDto = createBasketDtoWithPositions();
        final ResultActions request = mockMvc.perform(fileUpload("/basket").file(createDtoUpload(basketDto)));
        final String locationString = request.andReturn().getResponse().getHeader("Location");
        Long locationId = Long.valueOf(locationString.substring(locationString.lastIndexOf('/') + 1));
        // when
        final ResultActions request1 = mockMvc.perform(get("/basket/" + locationId + "/value"));
        final MockHttpServletResponse response = request1.andReturn().getResponse();
        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString()).isEqualToIgnoringCase("19799.76");
    }

    private BasketDto createBasketDto() {
        final BasketDto basketDto = new BasketDto();
        basketDto.setUserId(USER_ID);

        return basketDto;
    }

    private BasketDto createBasketDtoWithPositions() {
        final BasketDto dto = createBasketDto();
        BasketPositionDto basketPositionDto = new BasketPositionDto();
        basketPositionDto.setProductId(productId);
        basketPositionDto.setQuantity(2);

        BasketPositionDto basketPositionDto1 = new BasketPositionDto();
        basketPositionDto1.setProductId(productId1);
        basketPositionDto1.setQuantity(2);

        dto.setBasketPositions(Arrays.asList(basketPositionDto, basketPositionDto1));

        return dto;
    }

    private Long createProduct(String name, BigDecimal price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return productRepository.save(product).getId();
    }

    public MockMultipartFile createDtoUpload(final Object dto) throws JsonProcessingException {
        final String json = mapper.writeValueAsString(dto);
        String name = dto.getClass().getSimpleName();
        name = StringUtils.uncapitalize(name);

        return new MockMultipartFile(name, "", MediaType.APPLICATION_JSON_VALUE, json.getBytes());
    }
}
