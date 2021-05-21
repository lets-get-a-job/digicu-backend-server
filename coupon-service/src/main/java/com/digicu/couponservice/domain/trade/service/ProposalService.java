package com.digicu.couponservice.domain.trade.service;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.trade.dao.ProposalRepository;
import com.digicu.couponservice.domain.trade.dao.TradeRepository;
import com.digicu.couponservice.domain.trade.domain.Proposal;
import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.dto.TradeProposalRequest;
import com.digicu.couponservice.domain.trade.exception.ProposalNotFoundException;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import com.digicu.couponservice.global.util.fcm.FCMService;
import com.digicu.couponservice.global.util.fcm.FcmMessage;
import com.digicu.couponservice.global.util.fcm.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Transactional(rollbackFor=IOException.class)
@RequiredArgsConstructor
@Service
public class ProposalService {
    private final CouponFindDao couponFindDao;
    private final TradeService tradeService;
    private final TradeRepository tradeRepository;
    private final FCMService fcmService;
    private final ProposalRepository proposalRepository;

    public Proposal create(final TradeProposalRequest dto, final String phone) throws IOException {
        Coupon coupon = couponFindDao.findById(dto.getMyCouponId());
        if(phone.equals(coupon.getOwner())){
            coupon.verifyAspectState("DONE");
            coupon.setTradeState("TRADING");

            Trade trade = tradeService.findById(dto.getTradeId());
            Proposal proposal = Proposal.builder()
                    .trade(trade)
                    .coupon(coupon)
                    .owner(phone)
                    .build();
            trade.addProposal(proposal);
            tradeRepository.save(trade);

            MessageData messageData = MessageData.builder()
                    .title("Digicu 알림!")
                    .body(trade.getCoupon().getName() + "에 대한 교환 제시가 도착했습니다.")
                    .action("TRADE_PROPOSAL")
                    .proposal(String.valueOf(coupon.getId()))
                    .subject(String.valueOf(trade.getCoupon().getId()))
                    .build();
            FcmMessage fcmMessage = fcmService.makeMessage(trade.getOwner(), messageData);
            fcmService.sendMessage(fcmMessage);

            return proposal;
        } else {
            throw new AccessDeniedException(phone + " has not access for " + coupon.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void delete(final Long proposalId, final String phone){
        Proposal proposal = findById(proposalId);
        if(proposal.getOwner().equals(phone)){
            proposal.getCoupon().setTradeState("DONE");
            proposalRepository.delete(proposal);
        } else {
            throw new AccessDeniedException(phone + " has not access for " + proposal.getId(), ErrorCode.ACCESS_DENIED);
        }
    }


    //////////////////////////////////////////////////////
    public Proposal findById(final Long proposalId){
        Optional<Proposal> proposal = proposalRepository.findById(proposalId);
        proposal.orElseThrow(()-> new ProposalNotFoundException(proposalId));
        return proposal.get();
    }


}
