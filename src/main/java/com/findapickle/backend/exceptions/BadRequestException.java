package com.findapickle.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1003L;
    public BadRequestException(){
        super("Bad Request");
    }

    public BadRequestException(String message){
        super(message);
    }
}
