package com.digicu.couponservice.domain.coupon.dao;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.exception.CouponNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    public List<Coupon> findAllByEmail(final String email){
        Optional<List<Coupon>> coupons = couponRepository.findAllByOwner(email);
        return coupons.orElse(new ArrayList<>());
    }
}
