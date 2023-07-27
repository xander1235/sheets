package com.triffy.sheet.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public CustomException(String errorMsg) {
        super(errorMsg);
    }

    public CustomException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public abstract HttpStatus getHttpStatus();

}
