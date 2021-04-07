package com.digicu.couponservice.domain.coupon.service;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.domain.CouponSpec;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.test.IntergrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponServiceTest extends IntergrationTest{
    private CouponCreateRequest dto;
    private CouponSpec spec;

    @Autowired
    private CouponService couponService;

    @Before
    public void setUp() throws Exception{
        dto = CouponCreateRequest.builder()
                .couponSpecId(1234l)
                .owner("yonghu")
                .build();
        spec = CouponSpec.builder()
                .name("용후커피 10장 쿠폰")
                .type("PRODUCT")
                .validPeriod(60)
                .value(4500)
                .build();
    }

    @Test
    public void 쿠폰생성() {
        //when
        final Coupon coupon = couponService.create(dto, spec);
        System.out.println(coupon.toString());
        //then
        assertThat(coupon).isNotNull();
        assertThat(coupon.getOwner()).isEqualTo(dto.getOwner());
        assertThat(coupon.getName()).isEqualTo(spec.getName());
        assertThat(coupon.getCreatedDate()).isNotNull();
        assertThat(coupon.getType()).isEqualTo(spec.getType());
        assertThat(coupon.getValue()).isEqualTo(spec.getValue());
        assertThat(coupon.isUsed()).isEqualTo(false);
        //assertThat(coupon.getExpirationDate()).isAfter(coupon.getCreatedDate().toString());
    }
}