package com.fitnessapp.activityservice.dto;

import com.fitnessapp.activityservice.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityRequest {
    private String userId;
    private ActivityType activityType;
    private Double caloriesBurnt;
    private Integer duration;
    private LocalDateTime startTime;
}
