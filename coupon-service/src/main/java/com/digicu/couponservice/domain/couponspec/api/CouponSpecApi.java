package com.digicu.couponservice.domain.couponspec.api;

import com.digicu.couponservice.domain.couponspec.dao.CouponSpecFindDao;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecCreateRequest;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecUpdateRequest;
import com.digicu.couponservice.domain.couponspec.service.CouponSpecService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/coupon_spec")
public class CouponSpecApi {
    final private CouponSpecService couponSpecService;
    final private CouponSpecFindDao couponSpecFindDao;

    @PostMapping
    public ResponseEntity<CouponSpec> createCouponSpec(
            @NotNull @RequestHeader(value = "email") String email,
            @Valid @RequestBody CouponSpecCreateRequest dto) {

        CouponSpec spec = couponSpecService.create(email, dto);
        return new ResponseEntity<CouponSpec>(spec, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCouponSpec(
            @NotNull @RequestHeader(value = "email") String email,
            @Valid @PathVariable(name = "id") Long couponSpecId) {

            couponSpecService.delete(email, couponSpecId);
            return new ResponseEntity("삭제되었습니다.", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponSpec> updateCouponSpec(
            @NotNull @RequestHeader(value = "email") String email,
            @Valid @PathVariable(name ="id") Long couponSpecId,
            @Valid @RequestBody CouponSpecUpdateRequest dto) {

        CouponSpec spec = couponSpecService.update(email, couponSpecId, dto);
        return new ResponseEntity<CouponSpec>(spec, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponSpec> getCouponSpec(
            @Valid @PathVariable(name ="id") Long couponSpecId
    ){
        CouponSpec spec = couponSpecFindDao.findById(couponSpecId);
        return new ResponseEntity<CouponSpec>(spec, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CouponSpec>> getCouponSpecs(
            @Valid @RequestParam String email
    ){
        List<CouponSpec> specs = couponSpecFindDao.findAllByEmail(email);
        return new ResponseEntity<List<CouponSpec>>(specs, HttpStatus.OK);
    }

    @PostMapping("/img")
    public ResponseEntity<String> uploadImg(@RequestParam("file") MultipartFile multipartFile){
        File targetFile = new File("coupon-service/upload/" + multipartFile.getOriginalFilename());
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            return new ResponseEntity<String>(targetFile.getAbsolutePath(), HttpStatus.OK);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            e.printStackTrace();
            return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

