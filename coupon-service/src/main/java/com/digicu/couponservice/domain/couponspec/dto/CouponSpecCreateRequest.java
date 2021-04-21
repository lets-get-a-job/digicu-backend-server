package com.digicu.couponservice.domain.couponspec.dto;

import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class CouponSpecCreateRequest {

    @NotEmpty
    private String name;

    @Range(min = 1, message= "value may not be empty or null")
    private int value;

    @NotEmpty
    private String type;

    @Range(min = 1, message= "goal may not be empty or null")
    private int goal;

    @Range(min = 1, message= "period may not be empty or null")
    private int period;

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
