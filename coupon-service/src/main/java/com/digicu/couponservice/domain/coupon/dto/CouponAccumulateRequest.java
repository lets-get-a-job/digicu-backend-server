package com.digicu.couponservice.domain.coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CouponAccumulateRequest {

    @NotNull
    @JsonProperty(value = "num_acc")
    private Integer accNum;
}
