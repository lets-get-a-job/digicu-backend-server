package com.digicu.couponservice.domain.trade.api;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.trade.domain.Proposal;
import com.digicu.couponservice.domain.trade.dto.TradeProposalRequest;
import com.digicu.couponservice.domain.trade.service.ProposalService;
import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposal(@Valid @PathVariable(value = "id") Long id){
        Proposal proposal = proposalService.findById(id);
        return new ResponseEntity<Proposal>(proposal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity getProposal(
            @Valid @RequestHeader(name = "phone") String phone,
            @Valid @PathVariable(value = "id") Long proposalId){
        proposalService.delete(proposalId, phone);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<String> acceptProposal(
            @Valid @RequestHeader(name = "phone") String phone,
            @Valid @PathVariable(value = "id") Long proposalId) throws IOException {
        proposalService.accept(proposalId, phone);
        return new ResponseEntity<String>("교환에 성공했습니다.",HttpStatus.OK);
    }
}
