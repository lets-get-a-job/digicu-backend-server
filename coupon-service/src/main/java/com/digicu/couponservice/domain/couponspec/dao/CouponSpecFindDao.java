package com.digicu.couponservice.domain.couponspec.dao;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.domain.couponspec.exception.CouponSpecNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponSpecFindDao {
    final private CouponSpecRepository couponSpecRepository;

    public CouponSpec findById(Long id){
        final Optional<CouponSpec> spec = couponSpecRepository.findById(id);
        spec.orElseThrow(() -> new CouponSpecNotFoundException(id));
        return spec.get();
    }

    public List<CouponSpec> findAllByEmail(String email){
        final Optional<List<CouponSpec>> specs = couponSpecRepository.findAllByOwner(email);
        return specs.orElse(new ArrayList<>());
    }
}
