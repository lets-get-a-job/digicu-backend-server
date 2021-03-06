package com.digicu.couponservice.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    //COUPON_SPEC
    COUPONSPEC_NOT_FOUND(400, "존재하지 않는 쿠폰 상세정보입니다."),

    //COUPON
    COUPON_USED(400, "쿠폰이 이미 사용 됐습니다."),
    COUPON_EXPIRED(400,"만료된 쿠폰 입니다."),
    COUPON_FULL(400, "적립 최대 개수를 초과하는 입력입니다."),
    COUPON_IS_TRADING(400, "현재 거래중 상태인 쿠폰입니다."),
    COUPON_IS_NORMAL(400, "적립이 완료되지 않은 쿠폰입니다."),
    COUPON_NOT_FOUND(400, "존재하지 않는 쿠폰 입니다."),

    //TRADE
    TRADE_NOT_FOUND(400, "존재하지 않는 거래 정보 입니다."),
    PROPOSAL_NOT_FOUND(400, "존재하지 않는 교환 요청 입니다."),


    //FCM
    FCM_TOKEN_EXPIRED(400, "오랜시간 접속하지 않아 활동이 임시정지된 유저입니다."),

    //AUTHORIZATION
    ACCESS_DENIED(403, "접근할 수 없는 자원입니다."),

    //BINDING, VALIDATE
    INVALID_INPUT_VALUE(400, "유효하지 않은 파라미터 입니다."),

    //Entity
    ENTITY_NOT_FOUND(400, "존재하지 않는 비즈니스 자원입니다."),

    // CAN'T HANDLE
    INTERNAL_SERVER_ERROR(500, "관리자에게 문의해주십시오"),
    ;

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
