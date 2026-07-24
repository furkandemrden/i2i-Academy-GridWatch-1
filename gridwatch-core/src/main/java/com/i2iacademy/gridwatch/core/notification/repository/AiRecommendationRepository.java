package com.i2iacademy.gridwatch.core.notification.repository;

import com.i2iacademy.gridwatch.core.notification.entity.AiRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiRecommendationRepository extends JpaRepository<AiRecommendation, Long> {
    List<AiRecommendation> findByHomeIdOrderByCreatedAtDesc(Long homeId);
}