package com.digicu.couponservice.domain.couponspec.dto;

import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CouponSpecCreateRequest {
    private String name;
    private int value;
    private String type;
    private String owner;
    private int goal;
    private int period;

    public CouponSpec toEntity(){
        CouponSpec couponSpec = CouponSpec.builder()
                .name(name)
                .value(value)
                .type(type)
                .owner(owner)
                .period(period)
                .goal(goal)
                .build();
        return couponSpec;
    }
}
