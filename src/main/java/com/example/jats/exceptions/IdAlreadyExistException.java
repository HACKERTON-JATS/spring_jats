package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Id Already Exist Exception")
public class IdAlreadyExistException extends RuntimeException {
    public IdAlreadyExistException() {
        super("ID_ALREADY_EXIST");
    }
}
