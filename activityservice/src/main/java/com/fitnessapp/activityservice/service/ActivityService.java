package com.fitnessapp.activityservice.service;

import com.fitnessapp.activityservice.dto.AIServiceRequestDto;
import com.fitnessapp.activityservice.dto.ActivityRequest;
import com.fitnessapp.activityservice.dto.ActivityResponse;
import com.fitnessapp.activityservice.entity.Activity;
import com.fitnessapp.activityservice.producer.KafkaProducer;
import com.fitnessapp.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
    @Autowired
    private final ActivityRepository activityRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserValidationService userValidationService;

    @Autowired
    private final KafkaProducer kafkaProducer;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) throws ExecutionException, InterruptedException {
        if(!userValidationService.validateUser(activityRequest.getUserId())) {
            throw new RuntimeException("Invalid user ID. Please enter valid user ID");
        }

        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .activityType(activityRequest.getActivityType())
                .caloriesBurnt(activityRequest.getCaloriesBurnt())
                .duration(activityRequest.getDuration())
                .startTime(activityRequest.getStartTime())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            kafkaProducer.sendMessage(modelMapper.map(savedActivity, AIServiceRequestDto.class));
        } catch (Exception e) {
            log.error("Error occured while publishing to Kafka topic" + e.getMessage());
        }

        ActivityResponse resp = modelMapper.map(savedActivity, ActivityResponse.class);

        return resp;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        List<ActivityResponse> resp = activities
                .stream()
                .map(activity -> modelMapper.map(activity, ActivityResponse.class))
                .toList();
        return resp;
    }
}
