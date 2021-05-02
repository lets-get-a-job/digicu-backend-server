package com.digicu.couponservice.domain.coupon.api;

import com.digicu.couponservice.domain.coupon.dao.CouponFindDao;
import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.digicu.couponservice.domain.coupon.dto.CouponCreateRequest;
import com.digicu.couponservice.domain.coupon.service.CouponService;
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

    @PatchMapping("/{id}")
    public ResponseEntity<Coupon> useCoupon(
            @Valid @RequestHeader(name = "email") String email,
            @Valid @PathVariable(name = "id") Long couponId){
        Coupon coupon = couponService.use(couponId, email);
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
