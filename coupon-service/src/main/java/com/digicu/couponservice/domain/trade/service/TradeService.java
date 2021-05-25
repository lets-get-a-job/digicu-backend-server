package com.digicu.couponservice.domain.trade.service;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.trade.dao.TradeRepository;
import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.dto.TradeRegistRequest;
import com.digicu.couponservice.domain.trade.exception.TradeNotFoundException;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeService {
    private final CouponFindDao couponFindDao;
    private final TradeRepository tradeRepository;

    public Trade create(TradeRegistRequest dto, final String phone){
        Coupon coupon = couponFindDao.findById(dto.getCouponId());
        if(phone.equals(coupon.getOwner())){
            coupon.setTradeState("TRADING");
            Trade trade = dto.toEntity(coupon, phone);
            //trade.getCoupon().setTrade(trade);
            return tradeRepository.save(dto.toEntity(coupon, phone));
        } else {
            throw new AccessDeniedException(phone + "has not access for " + coupon.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void delete(final Long id, final String phone) {
        Trade trade = findById(id);
        if (trade.getOwner().equals(phone)) {
            Coupon coupon = trade.getCoupon();
            coupon.setTrade(null);
            coupon.setTradeState("DONE");
            tradeRepository.delete(trade);
        } else {
            throw new AccessDeniedException(phone + "has not access for " + trade.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public Trade findById(final Long id){
        Optional<Trade> trade = tradeRepository.findById(id);
        trade.orElseThrow(()->new TradeNotFoundException(id));
        return trade.get();
    }

    public List<Trade> findAllByPhone(final String phone){
        return tradeRepository.findAllByOwner(phone);
    }
}
