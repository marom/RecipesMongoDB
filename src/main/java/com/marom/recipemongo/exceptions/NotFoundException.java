package com.marom.recipemongo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    // not used, leave it as an example of possible usage
//    public NotFoundException() {
//        super();
//    }

    public NotFoundException(String message) {
        super(message);
    }

    // not used, leave it as an example of possible usage
//    public NotFoundException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
