package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already Participate Exception")
public class AlreadyParticipateException extends RuntimeException {
    public AlreadyParticipateException() {
        super("ALREADY_PARTICIPATE");
    }
}
