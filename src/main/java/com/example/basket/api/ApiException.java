package com.example.basket.api;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {
    private static final long serialVersionUID = 1975316797016840228L;

    private HttpStatus status;

    public ApiException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }

    public ApiException(HttpStatus status, String msg, Exception cause) {
        super(msg, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
