package com.digicu.couponservice.domain.coupon.exception;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponAccumulateException extends BusinessException {
    public CouponAccumulateException() {
        super(ErrorCode.COUPON_FULL);
    }
}
