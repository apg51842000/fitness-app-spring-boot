package com.fitnessapp.aiservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "recommandation_db")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String activityId;
    private String activityType;

    @Column(length = 1000)
    private String recommendation;

    @Column(length = 1000)
    private String improvement;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
