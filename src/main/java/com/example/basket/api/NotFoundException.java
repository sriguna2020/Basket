package com.example.basket.api;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    private static final long serialVersionUID = 5154130082865065005L;

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "Not found");
    }
}
