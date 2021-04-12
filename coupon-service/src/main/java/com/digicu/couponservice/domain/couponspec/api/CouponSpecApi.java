package com.digicu.couponservice.domain.couponspec.api;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecCreateRequest;
import com.digicu.couponservice.domain.couponspec.service.CouponSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon_spec")
public class CouponSpecApi {
    private CouponSpecService couponSpecService;

    @PutMapping
    public ResponseEntity<CouponSpec> createCouponSpec(@RequestBody CouponSpecCreateRequest dto) {
        CouponSpec spec = couponSpecService.create(dto);
        return new ResponseEntity<CouponSpec>(spec, HttpStatus.CREATED);
    }
}
