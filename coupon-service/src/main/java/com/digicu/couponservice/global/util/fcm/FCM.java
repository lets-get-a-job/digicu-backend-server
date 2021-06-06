package com.digicu.couponservice.global.util.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class FCM {
    private boolean validate_only;
    private Message message;

    @ToString
    @Getter
    @AllArgsConstructor
    public static class Message{
        private String token;
        private MessageData data;
    }
}
