package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already Join Exception")
public class AlreadyJoinException extends RuntimeException {
    public AlreadyJoinException() {
        super("ALREADY_JOIN");
    }
}
