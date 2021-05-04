package com.digicu.couponservice.domain.coupon.api;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.coupon.service.CouponService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
            @Valid @RequestBody @JsonProperty(value = "num_acc") int numAcc){
        Coupon coupon = couponService.accumulate(couponId, email, numAcc);
        return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
    }

    //    임시 조회 api 추후 상세한 조회 요구사항이 결정 되면
    //    CouponFindService 따로 만들어서 구현 할 것
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCoupon(
            @Valid @PathVariable(name = "id") Long couponId){
        Coupon coupon = couponFindDao.findById(couponId);
        return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getCoupons(@Valid @RequestParam String email){
        List<Coupon> coupons = couponFindDao.findAllByEmail(email);
        return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
    }
}
