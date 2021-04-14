package com.digicu.couponservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @GetMapping("/coupon/hi")
    public String hello(@RequestHeader Map<String, String> headers){
        headers.forEach((key, value)->{
            System.out.println(key + " : " + value);
        });
        return "hihihi";
    }
}
