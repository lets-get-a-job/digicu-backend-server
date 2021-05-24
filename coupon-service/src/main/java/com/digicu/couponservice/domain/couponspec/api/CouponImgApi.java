package com.digicu.couponservice.domain.couponspec.api;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/coupon/coupon_spec/img")
public class CouponImgApi {
    @PostMapping
    public ResponseEntity<String> uploadImg(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestHeader String host,
            HttpServletRequest request) {
        File targetFile = new File("src/main/resources/static/upload/" + multipartFile.getOriginalFilename());
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            String url = "http://" + host + "/static/" + multipartFile.getOriginalFilename();
            return new ResponseEntity<String>(url, HttpStatus.OK);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            e.printStackTrace();
            return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
