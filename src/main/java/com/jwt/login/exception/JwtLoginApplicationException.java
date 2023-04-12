package com.jwt.login.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebSocketApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    // 메시지 없이 에러코드만 있는 경우의 생성자
    public WebSocketApplicationException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}
