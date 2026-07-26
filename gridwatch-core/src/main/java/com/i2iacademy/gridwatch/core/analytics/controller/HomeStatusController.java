package com.i2iacademy.gridwatch.core.analytics.controller;

import com.i2iacademy.gridwatch.core.analytics.dto.HomeStatusResponse;
import com.i2iacademy.gridwatch.core.analytics.model.HomeMetrics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
@Tag(name = "Home Status", description = "Anlık ev metrikleri (Ignite)")
public class HomeStatusController {

    private static final String CACHE_NAME = "homeMetrics";

    private final IgniteClient igniteClient;

    @GetMapping("/{homeId}/status")
    @Operation(summary = "Bir evin anlık tüketim/ceza/anomali durumunu getir (Ignite'dan)")
    public ResponseEntity<HomeStatusResponse> getStatus(@PathVariable Long homeId) {
        ClientCache<Long, HomeMetrics> cache = igniteClient.getOrCreateCache(CACHE_NAME);
        HomeMetrics metrics = cache.get(homeId);

        if (metrics == null) {
            return ResponseEntity.ok(new HomeStatusResponse(homeId, BigDecimal.ZERO,
                    BigDecimal.ZERO, false, Map.of()));
        }

        return ResponseEntity.ok(new HomeStatusResponse(
                metrics.getHomeId(),
                metrics.getAccumulatedWatt(),
                metrics.getAccumulatedCost(),
                metrics.isPenaltyActive(),
                metrics.getApplianceAnomalyFlags()
        ));
    }
}