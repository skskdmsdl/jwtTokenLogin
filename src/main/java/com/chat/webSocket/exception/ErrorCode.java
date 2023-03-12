package com.chat.webSocket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "Member id is duplicated"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    ;

    // 어떤 에러인지에 따라 HttpStatus 변경을 위함
    private HttpStatus status;
    private String message;
}
