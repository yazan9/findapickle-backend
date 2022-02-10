package com.findapickle.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnknownErrorException()
    {
        super("An Unknown Error occured");
    }
}
