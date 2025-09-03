package com.fitnessapp.activityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    @Autowired
    private final WebClient userWebClient;

    public Boolean validateUser(String userId) {
        try{
            Boolean isValidUser = userWebClient
                    .get()
                    .uri("/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return isValidUser;
        } catch (WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("API request failed, please check if service is running or not.");
            }
        }

        return false;
    }
}
