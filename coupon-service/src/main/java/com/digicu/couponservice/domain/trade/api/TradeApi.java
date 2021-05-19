package com.digicu.couponservice.domain.trade.api;

import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.dto.TradeRegistRequest;
import com.digicu.couponservice.domain.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/trade")
public class TradeApi {
    private final TradeService tradeService;

    @PostMapping("/")
    public ResponseEntity<Trade> createTrade(
            @Valid @RequestHeader(name = "phone") final String phone,
            @Valid @RequestBody TradeRegistRequest dto){
        return new ResponseEntity<Trade>(tradeService.createTrade(dto, phone), HttpStatus.OK);
    }
}
