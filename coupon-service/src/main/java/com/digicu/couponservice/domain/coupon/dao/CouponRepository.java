package com.digicu.couponservice.domain.coupon.dao;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
