package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Like Not Found Exception")
public class LikeNotFoundException extends RuntimeException{
    public LikeNotFoundException() {
        super("Like_NOT_FOUND");
    }
}
