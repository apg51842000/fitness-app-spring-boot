package com.fitnessapp.aiservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fitnessapp.aiservice.dto.AIServiceRequestDto;
import com.fitnessapp.aiservice.dto.RecommendationDto;
import com.fitnessapp.aiservice.service.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final GeminiService geminiService;

    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(AIServiceRequestDto aiServiceRequestDto) throws JsonProcessingException {
        log.info("Received AI service request: {}", aiServiceRequestDto);
        RecommendationDto savedRecommendation = geminiService.getRecommendationAndImprovement(aiServiceRequestDto);

        log.info("Saved recommendation: {}", savedRecommendation);
    }
}
