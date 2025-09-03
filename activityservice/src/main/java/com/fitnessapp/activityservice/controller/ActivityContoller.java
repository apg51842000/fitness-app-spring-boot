package com.fitnessapp.activityservice.controller;

import com.fitnessapp.activityservice.dto.ActivityRequest;
import com.fitnessapp.activityservice.dto.ActivityResponse;
import com.fitnessapp.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Slf4j
public class ActivityContoller {
    @Autowired
    private final ActivityService activityService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@PathVariable String userId) {
        log.info("in get request");
        return ResponseEntity.ok(activityService.getUserActivities(userId));

    }

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest) throws ExecutionException, InterruptedException {
        ActivityResponse actResp = activityService.trackActivity(activityRequest);
        return ResponseEntity.ok(actResp);
    }
}
