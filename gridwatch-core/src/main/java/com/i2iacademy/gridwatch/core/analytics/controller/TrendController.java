package com.i2iacademy.gridwatch.core.analytics.controller;

import com.i2iacademy.gridwatch.core.analytics.dto.TrendPointResponse;
import com.i2iacademy.gridwatch.core.analytics.entity.ConsumptionSnapshot;
import com.i2iacademy.gridwatch.core.analytics.repository.ConsumptionSnapshotRepository;
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
@Tag(name = "Consumption Trend", description = "Geçmiş tüketim verisi (PostgreSQL)")
public class TrendController {

    private final ConsumptionSnapshotRepository consumptionSnapshotRepository;

    @GetMapping("/{homeId}/trend")
    @Operation(summary = "Bir evin gün gün tüketim geçmişini getir")
    public ResponseEntity<List<TrendPointResponse>> getTrend(@PathVariable Long homeId) {
        List<ConsumptionSnapshot> snapshots =
                consumptionSnapshotRepository.findByHomeIdOrderBySnapshotDateAsc(homeId);

        List<TrendPointResponse> response = snapshots.stream()
                .map(s -> new TrendPointResponse(s.getSnapshotDate(), s.getTotalWatt(), s.getTotalCost()))
                .toList();

        return ResponseEntity.ok(response);
    }
}