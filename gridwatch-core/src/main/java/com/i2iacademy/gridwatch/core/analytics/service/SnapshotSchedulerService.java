package com.i2iacademy.gridwatch.core.analytics.service;

import com.i2iacademy.gridwatch.core.analytics.entity.ConsumptionSnapshot;
import com.i2iacademy.gridwatch.core.analytics.model.HomeMetrics;
import com.i2iacademy.gridwatch.core.analytics.repository.ConsumptionSnapshotRepository;
import com.i2iacademy.gridwatch.core.home.entity.Home;
import com.i2iacademy.gridwatch.core.home.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotSchedulerService {

    private static final String CACHE_NAME = "homeMetrics";

    private final IgniteClient igniteClient;
    private final HomeRepository homeRepository;
    private final ConsumptionSnapshotRepository consumptionSnapshotRepository;

    @Scheduled(fixedRate = 60000)
    public void takeDailySnapshot() {
        ClientCache<Long, HomeMetrics> cache = igniteClient.getOrCreateCache(CACHE_NAME);
        List<Home> homes = homeRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Home home : homes) {
            HomeMetrics metrics = cache.get(home.getId());
            if (metrics == null) {
                continue;
            }

            Optional<ConsumptionSnapshot> existing =
                    consumptionSnapshotRepository.findByHomeIdAndSnapshotDate(home.getId(), today);

            ConsumptionSnapshot snapshot = existing.orElseGet(ConsumptionSnapshot::new);
            snapshot.setHome(home);
            snapshot.setSnapshotDate(today);
            snapshot.setTotalWatt(metrics.getAccumulatedWatt() != null ? metrics.getAccumulatedWatt() : java.math.BigDecimal.ZERO);
            snapshot.setTotalCost(metrics.getAccumulatedCost() != null ? metrics.getAccumulatedCost() : java.math.BigDecimal.ZERO);

            consumptionSnapshotRepository.save(snapshot);
        }

        log.debug("Daily snapshot cycle complete for {} homes", homes.size());
    }
}