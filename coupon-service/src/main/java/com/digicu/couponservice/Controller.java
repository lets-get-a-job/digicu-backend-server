package com.digicu.couponservice;

import com.digicu.couponservice.global.util.fcm.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("coupon/test")
public class Controller {
    private final FCMService fcmService;

    @GetMapping
    public String hello(@RequestHeader Map<String, String> headers){
        if(headers.containsKey("email")){
            System.out.println(headers.get("email"));
        } else{
            System.out.println("no email header");
        }
        final String fcmToken ="dDTJ5QDzSVGynnRlWZeuga:APA91bGZloyhrvtOtG561p-9IP_uVv5XEZfVr81QL5KcUnEqKlIYBnSnaAGbKYmUn-Bq4amGDJSEz3exlo2X7XpeOmHwflQcfA-7wqO-TQ3bx4q1wWEunj18EN_0oLiidWAtTZEO3fB7";
        return "hihihi";
    }
}
