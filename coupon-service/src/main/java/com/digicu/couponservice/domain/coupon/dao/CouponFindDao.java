package com.digicu.couponservice.domain.coupon.dao;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponNotFoundException;
import com.digicu.couponservice.domain.couponspec.exception.CouponSpecNotFoundException;
import com.digicu.couponservice.global.error.exception.EntityNotFoundException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CouponFindDao {
    final private CouponRepository couponRepository;

    public Coupon findById(final Long id){
        Optional<Coupon> coupon = couponRepository.findById(id);
        coupon.orElseThrow(() -> new CouponNotFoundException(id));
        return coupon.get();
    }
}
