package com.digicu.couponservice.domain.coupon.api;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponApi {

    final private CouponService couponService;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @RequestBody CouponCreateRequest dto) {
        Coupon coupon = couponService.create(dto,email);
        return new ResponseEntity<Coupon>(coupon, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @PathVariable(name = "id") Long couponId){
        couponService.delete(couponId, email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
