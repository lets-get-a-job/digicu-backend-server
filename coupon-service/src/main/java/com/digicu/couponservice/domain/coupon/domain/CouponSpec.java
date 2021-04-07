package com.digicu.couponservice.domain.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonFormat
@Builder
public class CouponSpec {
    private String type;
    private String name;
    private int validPeriod;
    private int value;
}