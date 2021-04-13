package com.digicu.couponservice.domain.couponspec.dao;

import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponSpecRepository extends JpaRepository<CouponSpec, Long> {
}
