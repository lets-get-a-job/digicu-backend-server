package com.digicu.couponservice.global.util.fcm;

import com.digicu.couponservice.global.error.exception.BusinessException;
import com.digicu.couponservice.global.error.exception.ErrorCode;

public class TargetUserFcmTokenExpired extends BusinessException {
    public TargetUserFcmTokenExpired() {
        super("FCM 을 보내려는 유저의 FCM Token이 만료됨", ErrorCode.FCM_TOKEN_EXPIRED);
    }
}
