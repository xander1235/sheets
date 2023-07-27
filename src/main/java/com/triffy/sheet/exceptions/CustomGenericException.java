package com.triffy.sheet.exceptions;

import org.springframework.http.HttpStatus;

public class CustomGenericException extends CustomException {

    private final HttpStatus status;

    public CustomGenericException(String errorMsg, HttpStatus status) {
        super(errorMsg);
        this.status = status;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
