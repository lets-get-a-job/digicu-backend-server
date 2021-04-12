package com.digicu.couponservice.domain.coupon.dto;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

;

@Getter
@Builder
public class CouponCreateRequest {
    private String owner;


    public Coupon toEntity(CouponSpec spec){
        return Coupon.builder()
                .name(spec.getName())
                .type(spec.getType())
                .expirationDate(calculateExpirationDate(spec.getPeriod()))
                .owner(owner)
                .value(spec.getValue())
                .build();
    }

    private LocalDate calculateExpirationDate(int period){
        return LocalDate.now().plusDays(period);
    }
}
