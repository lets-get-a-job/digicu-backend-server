package com.digicu.couponservice.domain.couponspec.api;

import com.digicu.couponservice.domain.couponspec.dto.CouponSpecCreateRequest;
import com.digicu.couponservice.test.IntergrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CouponSpecApiTest extends IntergrationTest {
    final String email = "dydgndlaek97@gmail.com";

    @Test
    public void 쿠폰상세정보_생성() throws Exception{
        //given
        CouponSpecCreateRequest dto = CouponSpecCreateRequest.builder()
                .name("용후 상점 10% 할인쿠폰")
                .owner(email)
                .goal(10)
                .period(60)
                .type("DISCOUNT")
                .value(10)
                .build();
        //when
        final ResultActions resultActions = requestCouponSpecCreate(dto);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(dto.getName()))
                ;
    }
    private ResultActions requestCouponSpecCreate(CouponSpecCreateRequest dto) throws Exception {
        return mvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}