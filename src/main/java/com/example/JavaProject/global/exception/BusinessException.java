package com.example.JavaProject.global.exception;

import lombok.Getter;

/** 서비스 계층에서 던지는 예외의 공통 부모. GlobalExceptionHandler가 ErrorCode에 맞는 응답으로 변환한다. */
@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getDefaultMessage());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
