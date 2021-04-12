package com.digicu.couponservice.domain.coupon.service;

import com.digicu.couponservice.domain.coupon.dao.CouponRepository;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon create(final CouponCreateRequest dto, CouponSpec spec){
        return couponRepository.save(dto.toEntity(spec));
    }
}
