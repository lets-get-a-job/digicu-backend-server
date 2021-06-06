package com.digicu.couponservice.domain.coupon.service;

import com.digicu.couponservice.domain.coupon.dao.CouponRepository;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.domain.CouponState;
import com.digicu.couponservice.domain.coupon.exception.CouponNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CouponFindService {
    final private CouponRepository couponRepository;

    public Coupon findById(final Long id){
        Optional<Coupon> coupon = couponRepository.findById(id);
        coupon.orElseThrow(() -> new CouponNotFoundException(id));
        return coupon.get();
    }

    public List<Coupon> findAllByPhone(final String phone){
        return couponRepository.findAllByOwner(phone);
    }

    public List<Coupon> findAllTrading(){
        return couponRepository.findAllByState(CouponState.TRADING);
    }


    public List<Coupon> findAllByPhoneAndStateAndCompany(final String phone, final CouponState state, final String issuer){
        return couponRepository.findAllByOwnerAndStateAndIssuer(phone, state, issuer);
    }
    public List<Coupon> findAllByPhoneAndState(final String phone, final CouponState state){
        return couponRepository.findAllByOwnerAndState(phone, state);
    }
}
