package com.fitnessapp.aiservice.config;

import com.fitnessapp.aiservice.dto.AIServiceRequestDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AIServiceRequestDto> kafkaListenerContainerFactory(
            ConsumerFactory<String, AIServiceRequestDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, AIServiceRequestDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // Retry: 3 attempts with 2-second delay
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                new FixedBackOff(2000L, 3)  // 2 sec delay, 3 retries
        );

        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}

