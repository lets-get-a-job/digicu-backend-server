package com.digicu.couponservice.global.util.fcm;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageData {
    @Getter
    public enum Action {
        TRADE_DONE(" 쿠폰의 교환이 완료되었습니다."),
        TRADE_PROPOSAL(" 쿠폰의 교환 요청이 도착했습니다."),
        CREATION(" 쿠폰이 발급되었습니다."),
        ACCUMULATION(" 쿠폰이 적립되었습니다."),
        TEST("테스트 메시지"),
        ;
        private String message;

        Action(String message) {
            this.message = message;
        }
    }

    private String title;
    private String body;
    private Action action;

    @Builder(access = AccessLevel.PRIVATE)
    private MessageData(String body, Action action) {
        this.title = "Digicu 알림!";
        this.body = body;
        this.action = action;
    }

    public static MessageData of(Action action, final String couponName){
        MessageData messageData = MessageData.builder()
                .action(action)
                .body(couponName + action.getMessage())
                .build();
        return messageData;
    }
}