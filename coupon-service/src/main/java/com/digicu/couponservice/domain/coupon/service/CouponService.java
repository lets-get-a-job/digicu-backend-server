package com.digicu.couponservice.domain.coupon.service;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.dao.CouponRepository;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.domain.CouponState;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.couponspec.dao.CouponSpecFindDao;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import com.digicu.couponservice.global.util.fcm.FCM;
import com.digicu.couponservice.global.util.fcm.FCMService;
import com.digicu.couponservice.global.util.fcm.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional(rollbackFor=IOException.class)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponSpecFindDao couponSpecFindDao;
    private final CouponFindDao couponFindDao;
    private final FCMService fcmService;

    public Coupon create(final CouponCreateRequest dto, final String email) throws IOException{
        CouponSpec spec = couponSpecFindDao.findById(dto.getCouponSpecId());
        if(email.equals(spec.getOwner())){
            Coupon created = couponRepository.save(dto.toEntity(spec, email));
            FCM fcm = fcmService.makeMessage(created.getOwner(), MessageData.Action.CREATION, created.getName());
            fcmService.sendMessage(fcm);
            return created;
        } else {
            throw new AccessDeniedException(email + " has not access for couponspec: " + spec.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void delete(final Long couponId, final String phone){
        Coupon coupon = couponFindDao.findById(couponId);
        if(phone.equals(coupon.getOwner())){
            coupon.verifyAspectNotState(CouponState.TRADING);
            couponRepository.delete(coupon);
        } else {
            throw new AccessDeniedException(phone + " has not access for coupon:" + couponId,
                    ErrorCode.ACCESS_DENIED);
        }
    }

    public Coupon use(final Long couponId, final String email) {
        Coupon coupon = couponFindDao.findById(couponId);
        if (email.equals(coupon.getIssuer())) {
            coupon.use();
            return coupon;
        } else {
            throw new AccessDeniedException(email + " has not access for coupon:" + couponId,
                    ErrorCode.ACCESS_DENIED);
        }
    }

    public Coupon accumulate(final Long couponId, final String email, final int numAcc) throws IOException {
        Coupon coupon = couponFindDao.findById(couponId);
        if(email.equals(coupon.getIssuer())){
            coupon.accumulate(numAcc);
            FCM fcm = fcmService.makeMessage(coupon.getOwner(), MessageData.Action.ACCUMULATION, coupon.getName());
            fcmService.sendMessage(fcm);
            return coupon;
        } else {
            throw new AccessDeniedException(email + " has not access for coupon:" + couponId,
                    ErrorCode.ACCESS_DENIED);
        }
    }
}
