package com.digicu.couponservice.global.util.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class MessageData {
    private String title;
    private String body;
    private String action;
    private String subject;
    private String proposal;
    private String email;
}