package com.digicu.couponservice.domain.couponspec.service;

import com.digicu.couponservice.domain.couponspec.dao.CouponSpecFindDao;
import com.digicu.couponservice.domain.couponspec.dao.CouponSpecRepository;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecCreateRequest;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecUpdateRequest;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
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
        if(email.equals(spec.getOwner())){
            couponSpecRepository.delete(spec);
        } else {
            throw new AccessDeniedException(email + "has no access for " + couponSpecId, ErrorCode.ACCESS_DENIED);
        }
    }

    public CouponSpec update(String email, Long couponSpecId, final CouponSpecUpdateRequest dto){
        CouponSpec spec = couponSpecFindDao.findById(couponSpecId);
        if(email.equals(spec.getOwner())){
            spec.update(dto);
            return couponSpecRepository.save(spec);
        } else {
            throw new AccessDeniedException(email + "has no access for " + couponSpecId, ErrorCode.ACCESS_DENIED);
        }
    }
}
