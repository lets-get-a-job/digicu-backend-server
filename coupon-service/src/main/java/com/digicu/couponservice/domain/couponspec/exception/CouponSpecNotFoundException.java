package com.digicu.couponservice.domain.couponspec.exception;


import com.digicu.couponservice.global.error.exception.EntityNotFoundException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class CouponSpecNotFoundException extends EntityNotFoundException {
    public CouponSpecNotFoundException(Long target) {
        super("coupon spec: " + target + " is not found", ErrorCode.COUPONSPEC_NOT_FOUND);
    }
}
