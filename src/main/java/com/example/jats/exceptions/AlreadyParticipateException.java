package com.example.jats.exceptions;

import com.example.jats.error.exception.BusinessException;
import com.example.jats.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already Participate Exception")
public class AlreadyParticipateException extends BusinessException {
    public AlreadyParticipateException() {
        super(ErrorCode.ALREADY_PARTICIPATE);
    }
}
