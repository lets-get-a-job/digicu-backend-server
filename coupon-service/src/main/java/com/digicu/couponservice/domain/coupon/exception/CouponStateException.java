package com.digicu.couponservice.domain.coupon.exception;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponStateException extends BusinessException {
    private CouponStateException(final String state, ErrorCode errorCode){
        super("state:" + state+ " coupon can't do that request", errorCode);
    }
    public static CouponStateException of(final String state){
        switch (state){
            case "NORMAL" :  return new CouponStateException(state,ErrorCode.COUPON_IS_NORMAL);
            case "TRADING" : return new CouponStateException(state, ErrorCode.COUPON_IS_TRADING);
            case "USED" : return new CouponStateException(state, ErrorCode.COUPON_USED);
            default: return new CouponStateException(state, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
