package com.digicu.couponservice.domain.coupon.api;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponAccumulateRequest;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.coupon.service.CouponService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponApi {

    final private CouponService couponService;
    final private CouponFindDao couponFindDao;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @RequestBody CouponCreateRequest dto) {
        Coupon coupon = couponService.create(dto,email);
        return new ResponseEntity<Coupon>(coupon, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @PathVariable(name = "id") Long couponId){
        couponService.delete(couponId, email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // pos쪽에서 use들어 올 때 사용자의 동의가 있는건지 확인 하는 루틴 필요
    // user password로 암호화 한 쿠폰 정보를 보내주면 될 것 같음.
    @PatchMapping("/use/{id}")
    public ResponseEntity<Coupon> useCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @PathVariable(name = "id") Long couponId){
        Coupon coupon = couponService.use(couponId, email);
        return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
    }

    @PatchMapping("/accumulate/{id}")
    public ResponseEntity<Coupon> accumulateCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @PathVariable(name = "id") Long couponId,
            @Valid @RequestBody CouponAccumulateRequest dto){
        Coupon coupon = couponService.accumulate(couponId, email, dto.getAccNum());
        return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCoupon(
            @Valid @PathVariable(name = "id") Long couponId){
        Coupon coupon = couponFindDao.findById(couponId);
        return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getCoupons(
            @RequestHeader(name = "email") String email,
            @RequestParam HashMap<String, Object> params){

        //no phone , yes email => email로 phone 조회해서 가져오기

        // 전체 trading
        if(!params.containsKey("phone")){
            List<Coupon> coupons = couponFindDao.findAllTrading();
            return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
        }
        // 특정 유저의 상태별 쿠폰
        else{
            String phone = String.valueOf(params.get("phone"));
            String state = String.valueOf(params.get("state"));
            List<Coupon> coupons = couponFindDao.findAllByPhoneAndState(phone, state);
            return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
        }
    }
}
