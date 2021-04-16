package com.digicu.couponservice.domain.couponspec.dao;

import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponSpecRepository extends JpaRepository<CouponSpec, Long> {
    Optional<List<CouponSpec>> findAllByOwner(String email);
}
