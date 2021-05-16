package com.digicu.couponservice.util.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
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
                .body(makeMessage(targetToken, title, body));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.exchange(req,String.class);
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

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage.Message.Notification notification = FcmMessage.Message.Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .build();
        FcmMessage.Message message = FcmMessage.Message.builder()
                .token(targetToken)
                .notification(notification)
                .build();
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(message)
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
}
