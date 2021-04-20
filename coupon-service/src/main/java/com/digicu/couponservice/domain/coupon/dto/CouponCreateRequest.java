package com.digicu.couponservice.domain.coupon.dto;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class CouponCreateRequest {

    @NotNull
    @Range(min = 1, message= "count may not be empty or null")
    private Integer count;

    @NotNull
    @Range(min = 1, message= "couponSpecId may not be empty or null")
    @JsonProperty(value = "coupon_spec_id")
    private Long couponSpecId;

    public Coupon toEntity(CouponSpec spec, final String email){
        Coupon coupon = Coupon.builder()
                .name(spec.getName())
                .count(count)
                .value(spec.getValue())
                .goal(spec.getGoal())
                .owner(email)
                .type(spec.getType())
                .expirationDate(LocalDate.now().plusDays(spec.getPeriod()))
                .build();
        return coupon;
    }
}
