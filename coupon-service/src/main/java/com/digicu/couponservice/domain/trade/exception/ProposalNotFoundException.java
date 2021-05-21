package com.digicu.couponservice.domain.trade.exception;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class ProposalNotFoundException extends BusinessException {
    public ProposalNotFoundException(final Long id) {
        super("proposal:" + id + "is not found", ErrorCode.PROPOSAL_NOT_FOUND);
    }
}
