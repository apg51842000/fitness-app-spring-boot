package com.fitnessapp.aiservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fitnessapp.aiservice.dto.AIServiceRequestDto;
import com.fitnessapp.aiservice.dto.RecommendationDto;
import com.fitnessapp.aiservice.entity.Recommendation;
import com.fitnessapp.aiservice.repository.RecommendationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class GeminiService {
    private final RestClient restClient;
    private final RecommendationRepository recommendationRepository;
    private final ModelMapper modelMapper;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Transactional
    public RecommendationDto getRecommendationAndImprovement(AIServiceRequestDto queryDto) throws JsonProcessingException {
        String queryTemplate = """
        Given the activity type, calories burnt and the duration of workout, suggest a {0} in 50 words.

        Activity type: {1},
        Calories burnt: {2},
        Duration: {3}
        """;
        String recommendationQuery = MessageFormat.format(queryTemplate,
                "good recommendation",
                queryDto.getActivityType(),
                queryDto.getCaloriesBurnt(),
                queryDto.getDuration()
        );

        String improvementQuery = MessageFormat.format(queryTemplate,
                "improvement",
                queryDto.getActivityType(),
                queryDto.getCaloriesBurnt(),
                queryDto.getDuration()
        );

        Map<String, Object> requestMap = Map.of(
                "contents", new Object[] {
                        Map.of(
                                "parts", new Object[] {
                                        Map.of(
                                                "text", recommendationQuery
                                        )
                                }
                        )
                }
        );

        JsonNode recommendationRoot = restClient
                .post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .body(requestMap)
                .retrieve()
                .body(JsonNode.class);

        requestMap = Map.of(
                "contents", new Object[] {
                        Map.of(
                                "parts", new Object[] {
                                        Map.of(
                                                "text", improvementQuery
                                        )
                                }
                        )
                }
        );

        JsonNode improvementRoot = restClient
                .post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .body(requestMap)
                .retrieve()
                .body(JsonNode.class);

        // Navigate to candidates[0].content.parts[0].text
        String recommendation = recommendationRoot.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        String improvement = improvementRoot.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        Recommendation newRecommendation = Recommendation
                .builder()
                .userId(queryDto.getUserId())
                .activityId(queryDto.getActivityId())
                .activityType(queryDto.getActivityType().toString())
                .recommendation(recommendation)
                .improvement(improvement)
                .build();

        Recommendation savedRecommendation = recommendationRepository.save(newRecommendation);

        return modelMapper.map(savedRecommendation, RecommendationDto.class);
    }
}
