package com.digicu.couponservice.domain.trade.api;

import com.digicu.couponservice.domain.trade.domain.Proposal;
import com.digicu.couponservice.domain.trade.dto.TradeProposalRequest;
import com.digicu.couponservice.domain.trade.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon/proposal")
public class ProposalApi {
    private final ProposalService proposalService;

    @PostMapping
    public ResponseEntity<Proposal> createProposal(
            @Valid @RequestBody TradeProposalRequest dto,
            @Valid @RequestHeader(name = "phone") String phone) throws IOException {
        Proposal proposal = proposalService.create(dto, phone);
        return new ResponseEntity<Proposal>(proposal, HttpStatus.OK) ;
    }
}
