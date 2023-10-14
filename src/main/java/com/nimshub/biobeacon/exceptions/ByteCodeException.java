package com.nimshub.biobeacon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ByteCodeException extends RuntimeException {
    public ByteCodeException(String message) {
        super(message);
    }
}
