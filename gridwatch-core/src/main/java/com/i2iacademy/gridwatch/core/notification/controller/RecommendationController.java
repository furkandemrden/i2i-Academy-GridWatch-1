package com.i2iacademy.gridwatch.core.notification.controller;

import com.i2iacademy.gridwatch.core.notification.entity.AiRecommendation;
import com.i2iacademy.gridwatch.core.notification.repository.AiRecommendationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
@Tag(name = "AI Recommendations", description = "Gemini tarafından üretilen tasarruf önerileri")
public class RecommendationController {

    private final AiRecommendationRepository aiRecommendationRepository;

    @GetMapping("/{homeId}/recommendations")
    @Operation(summary = "Bir evin AI tavsiye geçmişini getir")
    public ResponseEntity<List<AiRecommendation>> getRecommendations(@PathVariable Long homeId) {
        return ResponseEntity.ok(aiRecommendationRepository.findByHomeIdOrderByCreatedAtDesc(homeId));
    }
}