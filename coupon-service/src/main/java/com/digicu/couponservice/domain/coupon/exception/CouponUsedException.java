package com.digicu.couponservice.domain.coupon.exception;


import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponUsedException extends BusinessException {
    public CouponUsedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
    public CouponUsedException(){
        super(ErrorCode.COUPON_USED);
    }
}
