package com.example.basket.api;

import com.example.basket.model.BasketDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping(value = "/basket", produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "/basket", description = "the basket API")
public class BasketApi {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //    private final BasketMapper basketMapper;
//    private final BasketService basketService;
//
//    @Autowired
//    public BasketApi(final BasketMapper basketMapper, final BasketService basketService) {
//        this.basketMapper = basketMapper;
//        this.basketService = basketService;
//    }
//
//    // TODO user management/authorisation
//    @ApiOperation(value = "Gets basket with it's content.", response = BasketDto.class)
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Success", response = Void.class),
//            @ApiResponse(code = 400, message = "Basket with a given id does not exists", response = Void.class)})
//    @RequestMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    public ResponseEntity<BasketDto> getAddon(
//            @ApiParam(name = "basketId", value = "Basket id") @PathVariable final Long addonId)
//            throws NotFoundException {
//        final Basket basket = basketService.getBasket(addonId);
//        if (basket == null) {
//            throw new NotFoundException();
//        }
//        return new ResponseEntity<>(basketMapper.map(basket), HttpStatus.OK);
//    }

    @Autowired
    public BasketApi() {}

//    @ApiOperation(value = "getGreeting", nickname = "getGreeting")
//    @RequestMapping(method = RequestMethod.GET, path = "", produces = "application/json")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "User's name", required = false, dataType = "string", paramType = "query", defaultValue = "Niklas")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = BasketDto.class),
//            @ApiResponse(code = 401, message = "Unauthorized"),
//            @ApiResponse(code = 403, message = "Forbidden"),
//            @ApiResponse(code = 404, message = "Not Found"),
//            @ApiResponse(code = 500, message = "Failure")})
//    public BasketDto greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new BasketDto(counter.incrementAndGet(), Collections.emptyMap());
//    }

    @ApiOperation(value = "Pobiera zawartość koszyka.", response = BasketDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Zawartość koszyka została pobrana poprawnie", response = Void.class),
            @ApiResponse(code = 400, message = "Koszyk o podanym id nie istnieje", response = Void.class) })
    @RequestMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<BasketDto> getBasket(
            @ApiParam(name = "basketId", value = "Id koszyka") @PathVariable final Long basketId)
            throws NotFoundException {
//        final BasketDto addon = addonService.getAddon(addonId);
        final BasketDto basketDto = new BasketDto(counter.incrementAndGet(), Collections.emptyMap());
        if (basketDto == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }

}
