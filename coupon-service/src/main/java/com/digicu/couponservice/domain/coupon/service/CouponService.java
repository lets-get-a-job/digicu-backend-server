package com.digicu.couponservice.domain.coupon.service;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.dao.CouponRepository;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.couponspec.dao.CouponSpecFindDao;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponSpecFindDao couponSpecFindDao;
    private final CouponFindDao couponFindDao;

    public Coupon create(final CouponCreateRequest dto, final String email){
        CouponSpec spec = couponSpecFindDao.findById(dto.getCouponSpecId());
        if(email.equals(spec.getOwner())){
            return couponRepository.save(dto.toEntity(spec, email));
        } else {
            throw new AccessDeniedException(email + " has not access for couponspec: " + spec.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void delete(final Long couponId, final String email){
        Coupon coupon = couponFindDao.findById(couponId);
        if(email.equals(coupon.getOwner())){
            couponRepository.delete(coupon);
        } else {
            throw new AccessDeniedException(email + " has not access for coupon:" + couponId,
                    ErrorCode.ACCESS_DENIED);
        }
    }

    public Coupon use(final Long couponId, final String email) {
        Coupon coupon = couponFindDao.findById(couponId);
        if (email.equals(coupon.getOwner())) {
            coupon.use();
            return coupon;
        } else {
            throw new AccessDeniedException(email + " has not access for coupon:" + couponId,
                    ErrorCode.ACCESS_DENIED);
        }
    }
}
