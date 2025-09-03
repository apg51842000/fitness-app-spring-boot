package com.fitnessapp.activityservice.dto;

import com.fitnessapp.activityservice.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AIServiceRequestDto {
    private String id;
    private String userId;
    private String activityId;
    private ActivityType activityType;
    private Double caloriesBurnt;
    private Integer duration;
    private LocalDateTime startTime;
}
