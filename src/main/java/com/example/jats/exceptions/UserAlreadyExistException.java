package com.example.jats.exceptions;

import com.example.jats.error.exception.BusinessException;
import com.example.jats.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User Already Exist")
public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}
