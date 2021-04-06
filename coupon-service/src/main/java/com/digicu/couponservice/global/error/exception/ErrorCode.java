package com.digicu.couponservice.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    //COUPON
    COUPON_USED(400, "쿠폰이 이미 사용 됐습니다."),
    COUPON_EXPIRED(400,"만료된 쿠폰 입니다.");

    private int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
