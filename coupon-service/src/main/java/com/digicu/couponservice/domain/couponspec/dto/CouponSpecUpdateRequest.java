package com.digicu.couponservice.domain.couponspec.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
public class CouponSpecUpdateRequest {
    private String name;
    private int value;
    private String type;
    private int goal;
    private int period;
}
