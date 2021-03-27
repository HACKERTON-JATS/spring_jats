package com.example.jats.exceptions;

import com.example.jats.error.exception.BusinessException;
import com.example.jats.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid Access Exception")
public class InvalidAccessException extends BusinessException {
    public InvalidAccessException() {
        super(ErrorCode.INVALID_ACCESS);
    }
}
