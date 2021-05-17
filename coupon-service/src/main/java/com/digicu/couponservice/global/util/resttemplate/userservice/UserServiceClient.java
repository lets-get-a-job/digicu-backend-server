package com.digicu.couponservice.global.util.resttemplate.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class UserServiceClient {
    private final RestTemplate restTemplate;

    public String findEmailByPhone(final String phone){
        URI uri = UriComponentsBuilder
                .fromUriString("http://userservice")
                .path("/users/social")
                .queryParam("phone", phone)
                .encode()
                .build()
                .toUri();

        ResponseEntity<SocialResponse> restExchange = restTemplate.getForEntity(uri, SocialResponse.class);
        return restExchange.getBody().getEmail();
    }

    public String findFcmTokenByEmail(final String email){
        URI uri = UriComponentsBuilder
                .fromUriString("http://userservice")
                .path("/users/social/fcm")
                .queryParam("email", email)
                .encode()
                .build()
                .toUri();

        ResponseEntity<FcmTokenResponse> restExchange = restTemplate.getForEntity(uri, FcmTokenResponse.class);
        return restExchange.getBody().getFcmToken();
    }
}
