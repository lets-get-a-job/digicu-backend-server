package com.digicu.couponservice.domain.couponspec.service;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.couponspec.dao.CouponSpecFindDao;
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
    final private CouponSpecRepository couponSpecRepository;
    final private CouponSpecFindDao couponSpecFindDao;

    public CouponSpec create(String email, CouponSpecCreateRequest dto) {
        return couponSpecRepository.save(dto.toEntity(email));
    }

    public void delete(String email, Long couponSpecId){
        CouponSpec spec = couponSpecFindDao.findById(couponSpecId);
        couponSpecRepository.delete(spec);
    }
}
