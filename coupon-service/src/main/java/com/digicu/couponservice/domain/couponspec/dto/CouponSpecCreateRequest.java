package com.digicu.couponservice.domain.couponspec.dto;

import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class CouponSpecCreateRequest {

    @NotEmpty private String name;
    @NotNull private int value;
    @NotEmpty private String type;
    @NotNull private int goal;
    @NotNull private int period;

    public CouponSpec toEntity(final String email){
        CouponSpec couponSpec = CouponSpec.builder()
                .name(name)
                .value(value)
                .type(type)
                .owner(email)
                .period(period)
                .goal(goal)
                .build();
        return couponSpec;
    }
}
