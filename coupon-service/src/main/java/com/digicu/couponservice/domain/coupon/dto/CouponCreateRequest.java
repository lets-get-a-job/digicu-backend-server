package com.digicu.couponservice.domain.coupon.dto;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.domain.CouponSpec;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
public class CouponCreateRequest {
    private Long couponSpecId;
    private String owner;


    public Coupon toEntity(CouponSpec spec){
        return Coupon.builder()
                .name(spec.getName())
                .type(spec.getType())
                .expirationDate(calculateExpirationDate(spec.getValidPeriod()))
                .owner(owner)
                .value(spec.getValue())
                .build();
    }

    private LocalDate calculateExpirationDate(int period){
        return LocalDate.now().plusDays(period);
    }
}
