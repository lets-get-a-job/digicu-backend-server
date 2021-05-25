package com.digicu.couponservice.domain.trade.dao;

import com.digicu.couponservice.domain.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findAllByOwner(String phone);
    Optional<Trade> findByCouponId(Long couponId);
}
