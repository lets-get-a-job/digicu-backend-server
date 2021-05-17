package com.digicu.couponservice.global.util.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MessageData {
    private String action;
    private String subject;
    private String proposal;
    private String email;
}