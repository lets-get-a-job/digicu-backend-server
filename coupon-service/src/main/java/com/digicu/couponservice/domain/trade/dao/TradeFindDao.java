package com.digicu.couponservice.domain.trade.dao;

import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.exception.TradeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class TradeFindDao {
    private final TradeRepository tradeRepository;

    public Trade findById(final Long id){
        Optional<Trade> trade =tradeRepository.findById(id);
        trade.orElseThrow(()->new TradeNotFoundException(id));
        return trade.get();
    }

    public List<Trade> findAllByPhone(final String phone){
        return tradeRepository.findAllByOwner(phone);
    }
}
