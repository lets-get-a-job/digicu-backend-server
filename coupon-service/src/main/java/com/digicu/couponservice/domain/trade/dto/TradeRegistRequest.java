package com.digicu.couponservice.domain.trade.dto;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.trade.domain.Trade;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeRegistRequest {
    @NotNull
    private Long couponId;
    @NotNull
    private Integer boundValue;

    public Trade toEntity(Coupon coupon, final String phone){
        Trade trade = Trade.builder()
                .coupon(coupon)
                .owner(phone)
                .boundValue(boundValue)
                .build();
        return trade;
    }
}
