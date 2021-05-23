package com.digicu.couponservice.domain.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class TradeProposalRequest {
    @NotNull
    @JsonProperty(value = "trade_id")
    private Long tradeId;
    @NotNull
    @JsonProperty(value = "my_coupon_id")
    private Long myCouponId;
}
