package com.digicu.couponservice.global.error.exception;

public class AccessDeniedException extends RuntimeException {
    private ErrorCode errorCode;

    public AccessDeniedException(String message, ErrorCode code) {
        super(message);
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
