package com.digicu.couponservice.domain.coupon.exception;

import com.digicu.couponservice.global.error.exception.EntityNotFoundException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponNotFoundException extends EntityNotFoundException {
    public CouponNotFoundException(Long id) {
        super("coupon id:" + id + " is not found", ErrorCode.COUPON_NOT_FOUND);
    }
}
