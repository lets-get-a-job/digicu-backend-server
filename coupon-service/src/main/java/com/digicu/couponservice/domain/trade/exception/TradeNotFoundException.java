package com.digicu.couponservice.domain.trade.exception;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class TradeNotFoundException extends BusinessException {
    public TradeNotFoundException(final Long id) {
        super("trade:" + id + " not found", ErrorCode.TRADE_NOT_FOUND);
    }
}
