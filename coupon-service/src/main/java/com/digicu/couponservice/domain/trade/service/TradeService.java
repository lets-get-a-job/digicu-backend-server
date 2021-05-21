package com.digicu.couponservice.domain.trade.service;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.trade.dao.TradeFindDao;
import com.digicu.couponservice.domain.trade.dao.TradeRepository;
import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.dto.TradeRegistRequest;
import com.digicu.couponservice.global.error.exception.AccessDeniedException;
import com.digicu.couponservice.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeService {
    private final CouponFindDao couponFindDao;
    private final TradeRepository tradeRepository;
    private final TradeFindDao tradeFindDao;

    public Trade create(TradeRegistRequest dto, final String phone){
        Coupon coupon = couponFindDao.findById(dto.getCouponId());
        if(phone.equals(coupon.getOwner())){
            coupon.setTradeState("TRADING");
            return tradeRepository.save(dto.toEntity(coupon, phone));
        } else {
            throw new AccessDeniedException(phone + "has not access for " + coupon.getId(), ErrorCode.ACCESS_DENIED);
        }
    }

    public void delete(final Long id, final String phone){
        Trade trade = tradeFindDao.findById(id);
        if(trade.getOwner().equals(phone)) {
            trade.getCoupon().setTradeState("DONE");
            tradeRepository.delete(trade);
        } else {
            throw new AccessDeniedException(phone + "has not access for " + trade.getId(), ErrorCode.ACCESS_DENIED);
        }
    }
}
