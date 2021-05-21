package com.digicu.couponservice;

import com.digicu.couponservice.global.util.fcm.FCMService;
import com.digicu.couponservice.global.util.fcm.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/test")
public class TestApi {
    private final FCMService fcmService;

    @PostMapping("/fcm/{phone}")
    public String fcmEcho(@PathVariable String phone) throws IOException{
        MessageData msgData = MessageData.builder()
                .title("DIgicu")
                .body("test message")
                .action("TEST")
                .build();
        fcmService.sendMessage(fcmService.makeMessage(phone, msgData));
        return "OK";
    }

    @GetMapping("/header")
    public Map<String,String> headerEcho(@RequestHeader Map<String, String> headers){
        if(headers.containsKey("email")){
            System.out.println(headers.get("email"));
        }
        if(headers.containsKey("phone")){
            System.out.println(headers.get("phone"));
        }
        return headers;
    }
}
