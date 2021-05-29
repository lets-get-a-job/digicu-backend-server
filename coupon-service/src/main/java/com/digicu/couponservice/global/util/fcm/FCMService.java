package com.digicu.couponservice.global.util.fcm;

import com.digicu.couponservice.global.util.resttemplate.userservice.UserServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class FCMService {
    private final ObjectMapper objectMapper;
    private final UserServiceClient userServiceClient;

    public void sendMessage(FCM message) throws IOException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://fcm.googleapis.com")
                .path("/v1/projects/digicu/messages:send")
                .encode()
                .build()
                .toUri();

        RequestEntity<String> req = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getAccessToken())
                .body(objectMapper.writeValueAsString(message));

        System.out.println(req.getBody().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.exchange(req, String.class);
        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/digicu-firebase-key.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped("https://www.googleapis.com/auth/cloud-platform");

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public FCM makeMessage(final String phone, MessageData.Action action, final String couponName){
        final String email = userServiceClient.findEmailByPhone(phone);
        final String targetToken = userServiceClient.findFcmTokenByEmail(email);

        FCM.Message message = new FCM.Message(targetToken, MessageData.of(action, couponName));
        return new FCM(false, message);
    }
}
