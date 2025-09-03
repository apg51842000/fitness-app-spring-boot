package com.fitnessapp.aiservice.service;

import com.fitnessapp.aiservice.dto.RecommendationDto;
import com.fitnessapp.aiservice.entity.Recommendation;
import com.fitnessapp.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    @Autowired
    private final RecommendationRepository recommendationRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public RecommendationDto getUserRecommendation(String userId) {
        Recommendation recommendation = recommendationRepository.findByUserId(userId);
        return modelMapper.map(recommendation, RecommendationDto.class);
    }

    public RecommendationDto getActivityRecommendation(String activityId) {
        Recommendation recommendation = recommendationRepository.findByActivityId(activityId);
        return modelMapper.map(recommendation, RecommendationDto.class);
    }
}
