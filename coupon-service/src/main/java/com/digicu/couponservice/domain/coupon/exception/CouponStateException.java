package com.digicu.couponservice.domain.coupon.exception;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponStateException extends BusinessException {
    private CouponStateException(ErrorCode errorCode){
        super(errorCode);
    }
    public static CouponStateException of(final String state){
        switch (state){
            case "NORMAL" :  return new CouponStateException(ErrorCode.COUPON_IS_NORMAL);
            case "TRADING" : return new CouponStateException(ErrorCode.COUPON_IS_TRADING);
            case "USED" : return new CouponStateException(ErrorCode.COUPON_USED);
            default: return new CouponStateException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
