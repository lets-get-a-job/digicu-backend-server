package com.digicu.couponservice.domain.coupon.dao;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon>{
    List<Coupon> findAllByOwner(String phone);
    List<Coupon> findAllByOwnerAndStateAndIssuer(String phone, String state, String issuer);
    List<Coupon> findAllByState(String state);
    List<Coupon> findAllByOwnerAndState(String owner, String state);

}
