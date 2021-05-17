package com.digicu.couponservice.global.util.resttemplate.userservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FcmTokenResponse {
    @JsonProperty(value = "fcm_token")
    private String fcmToken;
}
