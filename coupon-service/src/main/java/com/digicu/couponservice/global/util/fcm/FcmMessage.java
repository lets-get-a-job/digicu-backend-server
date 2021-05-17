package com.digicu.couponservice.global.util.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Notification notification;
        private String token;
        private MessageData data;

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Notification{
            private String title;
            private String body;
        }

    }
}
