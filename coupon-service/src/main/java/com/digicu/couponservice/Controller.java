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
public class Controller {
    private final FCMService fcmService;

    @PostMapping("/fcm/{phone}")
    public String fcmEcho(@RequestHeader Map<String, String> headers, @PathVariable String phone) throws IOException{
        if(headers.containsKey("email")){
            System.out.println(headers.get("email"));
        } else{
            System.out.println("no email header");
        }
        MessageData msgData = MessageData.builder()
                .title("DIgicu")
                .body("test message")
                .action("TEST")
                .build();
        fcmService.sendMessage(fcmService.makeMessage(phone, msgData));
        return "OK";
    }
}
