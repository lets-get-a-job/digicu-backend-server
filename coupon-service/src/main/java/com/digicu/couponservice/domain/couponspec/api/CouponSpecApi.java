package com.digicu.couponservice.domain.couponspec.api;

import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecCreateRequest;
import com.digicu.couponservice.domain.couponspec.service.CouponSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon_spec")
public class CouponSpecApi {
    final private CouponSpecService couponSpecService;

    @PostMapping
    public ResponseEntity<CouponSpec> createCouponSpec(
            @NotNull @RequestHeader(value = "email") String email,
            @Valid @RequestBody CouponSpecCreateRequest dto) {

        CouponSpec spec = couponSpecService.create(email, dto);
        return new ResponseEntity<CouponSpec>(spec, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCouponSpec(
            @NotNull @RequestHeader(value = "email") String email,
            @Valid @PathVariable(name = "id") Long couponSpecId) {

            couponSpecService.delete(email, couponSpecId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
