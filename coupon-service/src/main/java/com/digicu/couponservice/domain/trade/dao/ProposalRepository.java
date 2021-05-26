package com.digicu.couponservice.domain.trade.dao;

import com.digicu.couponservice.domain.trade.domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findAllByCouponId(Long couponId);
}
