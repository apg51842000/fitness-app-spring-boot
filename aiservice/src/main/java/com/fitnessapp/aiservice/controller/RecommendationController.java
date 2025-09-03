package com.fitnessapp.aiservice.controller;

import com.fitnessapp.aiservice.dto.RecommendationDto;
import com.fitnessapp.aiservice.entity.Recommendation;
import com.fitnessapp.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<RecommendationDto> getUserRecommendation(@PathVariable String userId) {
        RecommendationDto recommendationDto = recommendationService.getUserRecommendation(userId);
        return ResponseEntity.ok(recommendationDto);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<RecommendationDto> getActivityRecommendation(@PathVariable String activityId) {
        RecommendationDto recommendationDto = recommendationService.getActivityRecommendation(activityId);
        return ResponseEntity.ok(recommendationDto);
    }
}
