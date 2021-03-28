package com.example.jats.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode code;

    public BusinessException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
