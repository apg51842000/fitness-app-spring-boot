package com.fitnessapp.activityservice.producer;

import com.fitnessapp.activityservice.dto.AIServiceRequestDto;
import com.fitnessapp.activityservice.entity.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, AIServiceRequestDto> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;

    public void sendMessage(AIServiceRequestDto aiServiceRequestDto) throws ExecutionException, InterruptedException {
        log.info("Received request for AI service");
        kafkaTemplate.send(topic, aiServiceRequestDto).get();
        log.info("Sent request for AI service");
    }
}
