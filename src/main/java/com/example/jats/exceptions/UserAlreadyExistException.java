package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User Already Exist")
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("USER_ALREADY_EXIST");
    }
}
