package com.digicu.couponservice.domain.coupon.domain;

import lombok.Getter;

@Getter
public enum CouponState {
    TRADING,
    TRADING_REQ,
//    EXPIRED,
    NORMAL,
    USED,
    DONE
    ;
}
