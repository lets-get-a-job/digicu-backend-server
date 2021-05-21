package com.digicu.couponservice.domain.trade.api;

import com.digicu.couponservice.domain.trade.dao.TradeFindDao;
import com.digicu.couponservice.domain.trade.dao.TradeRepository;
import com.digicu.couponservice.domain.trade.domain.Trade;
import com.digicu.couponservice.domain.trade.dto.TradeRegistRequest;
import com.digicu.couponservice.domain.trade.service.TradeService;
import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/trade")
public class TradeApi {
    private final TradeService tradeService;
    private final TradeFindDao tradeFindDao;

    @PostMapping
    public ResponseEntity<Trade> createTrade(
            @Valid @RequestHeader(name = "phone") final String phone,
            @Valid @RequestBody TradeRegistRequest dto){
        return new ResponseEntity<Trade>(tradeService.create(dto, phone), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTrade(@Valid @PathVariable(value = "id") Long id){
        Trade trade = tradeFindDao.findById(id);
        return new ResponseEntity<Trade>(trade, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrade(
            @Valid @PathVariable(value = "id") Long id,
            @Valid @RequestHeader(name = "phone") String phone){
        tradeService.delete(id,phone);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getTrades(
            @RequestParam HashMap<String, Object> params) {
        List<Trade> trades = new ArrayList<>();
        if(params.containsKey("phone")){
            trades = tradeFindDao.findAllByPhone(String.valueOf(params.get("phone")));
        }
        return new ResponseEntity<List<Trade>>(trades, HttpStatus.OK);
    }
}
