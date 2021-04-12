package com.digicu.couponservice.domain.coupon.api;

import com.digicu.couponservice.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponApi {

    private CouponService couponService;

    /*
    ResponseEntity createCoupon(Long couponSpecId, CouponCreateRequest dto) {
        // dto.getCouponSpecId()로 spec 정보 받아오기
        CouponSpec spec = new CouponSpec();
        final Coupon coupon = couponService.create(dto, spec);
        return new CouponResponse(coupon);
    }

     */
}
