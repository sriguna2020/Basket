package com.example.basket.api;

import com.example.basket.entity.Basket;
import com.example.basket.mapper.BasketMapper;
import com.example.basket.mapper.BasketPositionMapper;
import com.example.basket.model.BasketDto;
import com.example.basket.model.BasketPositionDto;
import com.example.basket.service.BasketService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Controller
@RequestMapping(value = "/basket")
@Api(value = "/basket", description = "the basket API")
public class BasketController {

    private BasketMapper basketMapper;
    private BasketService basketService;
    private BasketPositionMapper basketPositionMapper;

    @Autowired
    public BasketController(final BasketMapper basketMapper, final BasketService basketService,
                            final BasketPositionMapper basketPositionMapper) {
        this.basketMapper = basketMapper;
        this.basketService = basketService;
        this.basketPositionMapper = basketPositionMapper;
    }

    public BasketController() {}

    @ApiOperation(value = "Pobiera zawartość koszyka.", response = BasketDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Zawartość koszyka została pobrana poprawnie", response = Void.class),
            @ApiResponse(code = 400, message = "Koszyk o podanym id nie istnieje", response = Void.class) })
    @RequestMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<BasketDto> getBasket(
            @ApiParam(name = "basketId", value = "Id koszyka") @PathVariable final Long basketId)
            throws NotFoundException {
        final Basket basket = basketService.getBasket(basketId);
        if (basket == null) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(basketMapper.map(basket), HttpStatus.OK);
    }

    @ApiOperation(value = "Pobiera wartość koszyka.", response = BasketDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Zawartość koszyka została pobrana poprawnie", response = Void.class),
            @ApiResponse(code = 400, message = "Koszyk o podanym id nie istnieje", response = Void.class) })
    @RequestMapping(value = "/{basketId}/value", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Double> getBasketValue(
            @ApiParam(name = "basketId", value = "Id koszyka") @PathVariable final Long basketId)
            throws NotFoundException {
        return new ResponseEntity<>(basketService.getBasketValue(basketId).doubleValue(), HttpStatus.OK);
    }

    @ApiOperation(value = "Znajduje produkt w koszyku.", response = BasketPositionDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Operacja wykonana poprawnie", response = Void.class),
            @ApiResponse(code = 400, message = "Koszyk o podanym id nie istnieje", response = Void.class) })
    @RequestMapping(value = "/{basketId}/find/{phrase}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<BasketPositionDto> getBasketPosition(
            @ApiParam(name = "basketId", value = "Fraza do wyszukania") @PathVariable final String phrase,
            @ApiParam(name = "basketId", value = "Id koszyka") @PathVariable final Long basketId)
            throws NotFoundException {
        return new ResponseEntity<>(basketPositionMapper.map(basketService.findProduct(basketId, phrase)), HttpStatus.OK);
    }

    @ApiOperation(value = "Zapisuje koszyk.", response = Void.class)
    @ApiResponses({ @ApiResponse(code = 201, message = "Koszyk został utworzony", response = Void.class),
            @ApiResponse(code = 204, message = "Koszyk został zaktualizowany", response = Void.class) })
    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Void> saveBasket(
            @ApiParam("Nowo tworzony koszyk") @RequestPart("basketDto") final BasketDto basketDto,
            final UriComponentsBuilder uriBuilder)
            throws NotFoundException {
        Basket entity = basketMapper.map(basketDto);
        if (basketDto.getId() == null) {
            final Basket saved = basketService.createBasket(entity);
            final UriComponents uriComponents = uriBuilder.path("/basket/{id}").buildAndExpand(saved.getId());
            final HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());
            headers.setAccessControlExposeHeaders(Collections.singletonList("location"));
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            basketService.updateBasket(entity);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
