package com.digicu.couponservice.domain.coupon.dao;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.domain.CouponState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon>{
    List<Coupon> findAllByOwner(String phone);
    List<Coupon> findAllByOwnerAndStateAndIssuer(String phone, CouponState state, String issuer);
    List<Coupon> findAllByState(CouponState state);
    List<Coupon> findAllByOwnerAndState(String owner, CouponState state);
}
