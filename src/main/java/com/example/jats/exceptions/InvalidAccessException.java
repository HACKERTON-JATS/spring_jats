package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid Access Exception")
public class InvalidAccessException extends RuntimeException {
    public InvalidAccessException() {
        super("INVALID_ACCESS");
    }
}
