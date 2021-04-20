package com.digicu.couponservice.domain.coupon.exception;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponExpireException extends BusinessException {
    public CouponExpireException() {
        super(ErrorCode.COUPON_EXPIRED);
    }
}
