package com.digicu.couponservice.domain.trade.service;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.domain.CouponState;
import com.digicu.couponservice.domain.trade.dao.ProposalRepository;
import com.digicu.couponservice.domain.trade.domain.Proposal;
import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.dto.TradeProposalRequest;
import com.digicu.couponservice.domain.trade.exception.ProposalNotFoundException;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import com.digicu.couponservice.global.util.fcm.FCM;
import com.digicu.couponservice.global.util.fcm.FCMService;
import com.digicu.couponservice.global.util.fcm.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor=IOException.class)
@RequiredArgsConstructor
@Service
public class ProposalService {
    private final CouponFindDao couponFindDao;
    private final TradeService tradeService;
    private final FCMService fcmService;
    private final ProposalRepository proposalRepository;

    public Proposal create(final TradeProposalRequest dto, final String phone) throws IOException {
        Coupon coupon = couponFindDao.findById(dto.getMyCouponId());
        if(phone.equals(coupon.getOwner())){
            coupon.verifyAspectState(Arrays.asList(CouponState.DONE));
            coupon.setTradeState(CouponState.TRADING_REQ);

            Trade trade = tradeService.findById(dto.getTradeId());
            Proposal proposal = Proposal.builder()
                    .trade(trade)
                    .coupon(coupon)
                    .owner(phone)
                    .build();

            FCM fcm = fcmService.makeMessage(trade.getOwner(),
                    MessageData.Action.TRADE_PROPOSAL,
                    trade.getCoupon().getName());
            fcmService.sendMessage(fcm);

            return proposalRepository.save(proposal);
        } else {
            throw new AccessDeniedException(phone + " has not access for " + coupon.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void delete(final Long proposalId, final String phone){
        Proposal proposal = findById(proposalId);
        if(proposal.getOwner().equals(phone)){
            proposal.getCoupon().setTradeState(CouponState.DONE);
            proposalRepository.delete(proposal);
        } else {
            throw new AccessDeniedException(phone + " has not access for " + proposal.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void accept(final Long proposalId, final String phone) throws IOException{
        Proposal proposal = findById(proposalId);
        Trade trade = proposal.getTrade();
        if(trade.getOwner().equals(phone)){
            Coupon tradeCoupon = trade.getCoupon();
            Coupon proposalCoupon = proposal.getCoupon();

            tradeCoupon.setTradeState(CouponState.DONE);
            proposalCoupon.setTradeState(CouponState.DONE);

            tradeCoupon.setOwner(proposal.getOwner());
            proposalCoupon.setOwner(trade.getOwner());

            FCM fcmToProposal = fcmService.makeMessage(proposal.getOwner(), MessageData.Action.TRADE_DONE, proposal.getCoupon().getName());
            FCM fcmToTrade = fcmService.makeMessage(trade.getOwner(), MessageData.Action.TRADE_DONE, trade.getCoupon().getName());
            fcmService.sendMessage(fcmToProposal);
            fcmService.sendMessage(fcmToTrade);
        }
    }

    //////////////////////////////////////////////////////
    public Proposal findById(final Long proposalId){
        Optional<Proposal> proposal = proposalRepository.findById(proposalId);
        proposal.orElseThrow(()-> new ProposalNotFoundException(proposalId));
        return proposal.get();
    }

    public List<Proposal> findAllByCouponId(final Long couponId){
        return proposalRepository.findAllByCouponId(couponId);
    }
}
