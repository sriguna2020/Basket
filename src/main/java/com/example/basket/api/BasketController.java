package com.example.basket.api;

import com.example.basket.entity.Basket;
import com.example.basket.mapper.BasketMapper;
import com.example.basket.model.BasketDto;
import com.example.basket.service.BasketService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/basket")
@Api(value = "/basket", description = "the basket API")
public class BasketController {

    private BasketMapper basketMapper;
    private BasketService basketService;

    @Autowired
    public BasketController(final BasketMapper basketMapper, final BasketService basketService) {
        this.basketMapper = basketMapper;
        this.basketService = basketService;
    }

    public BasketController() {}

    // TODO - user - basket security!
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
}
