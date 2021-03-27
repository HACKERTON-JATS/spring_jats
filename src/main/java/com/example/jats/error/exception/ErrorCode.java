package com.example.jats.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    ALREADY_PARTICIPATE(409, "user already participated"),
    CAMPAIGN_NOT_FOUND(404, "campaign not found"),
    INVALID_ACCESS(401, "invalid access"),
    INVALID_TOKEN(401, "invalid token"),
    LIKE_NOT_FOUND(404, "like not found"),
    USER_ALREADY_EXIST(409, "user already exist"),
    USER_NOT_FOUND(404, "user not found");

    private final int status;
    private final String message;
}
