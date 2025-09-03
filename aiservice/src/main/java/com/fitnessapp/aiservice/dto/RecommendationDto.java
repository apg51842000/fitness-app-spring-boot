package com.fitnessapp.aiservice.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class RecommendationDto {
    private String id;
    private String userId;
    private String activityId;
    private String activityType;
    private String recommendation;
    private String improvement;
    private LocalDateTime createdAt;
}
