package com.digicu.couponservice.domain.couponspec.service;

import com.digicu.couponservice.domain.couponspec.dao.CouponSpecRepository;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponSpecService {
    private CouponSpecRepository couponSpecRepository;

    public CouponSpec create(CouponSpecCreateRequest dto){
        return couponSpecRepository.save(dto.toEntity());
    }
}
