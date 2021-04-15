package com.digicu.couponservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @GetMapping("/hi")
    public String hello(@RequestHeader Map<String, String> headers){
        if(headers.containsKey("email")){
            System.out.println(headers.get("email"));
        } else{
            System.out.println("no email header");
        }
        return "hihihi";
    }
}
