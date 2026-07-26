package com.i2iacademy.gridwatch.core.messaging;

import com.i2iacademy.gridwatch.core.analytics.model.HomeMetrics;
import com.i2iacademy.gridwatch.core.billing.entity.BillingAccount;
import com.i2iacademy.gridwatch.core.billing.repository.BillingAccountRepository;
import com.i2iacademy.gridwatch.core.home.entity.Appliance;
import com.i2iacademy.gridwatch.core.home.repository.ApplianceRepository;
import com.i2iacademy.gridwatch.core.notification.service.AiNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.i2iacademy.gridwatch.core.config.KafkaTopicConfig.TELEMETRY_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryConsumerService {

    private static final String CACHE_NAME = "homeMetrics";
    private static final int BREACH_THRESHOLD = 3;

    private final IgniteClient igniteClient;
    private final BillingAccountRepository billingAccountRepository;
    private final ApplianceRepository applianceRepository;
    private final AiNotificationService aiNotificationService;

    @KafkaListener(topics = TELEMETRY_TOPIC, groupId = "gridwatch-core")
    public void consume(TelemetryEvent event) {
        Optional<BillingAccount> billingOpt = billingAccountRepository.findByHomeId(event.getHomeId());
        Optional<Appliance> applianceOpt = applianceRepository.findById(event.getApplianceId());

        if (billingOpt.isEmpty() || applianceOpt.isEmpty()) {
            log.warn("Skipping telemetry: missing billing account or appliance for homeId={}, applianceId={}",
                    event.getHomeId(), event.getApplianceId());
            return;
        }

        BillingAccount billing = billingOpt.get();
        Appliance appliance = applianceOpt.get();

        ClientCache<Long, HomeMetrics> cache = igniteClient.getOrCreateCache(CACHE_NAME);
        HomeMetrics metrics = cache.get(event.getHomeId());
        if (metrics == null) {
            metrics = new HomeMetrics();
            metrics.setHomeId(event.getHomeId());
        }

        boolean penaltyWasActive = metrics.isPenaltyActive();

        BigDecimal activeRate = penaltyWasActive ? billing.getPenaltyRate() : billing.getNormalRate();
        BigDecimal cost = event.getCurrentWatt().multiply(activeRate);
        metrics.setAccumulatedCost(metrics.getAccumulatedCost().add(cost));
        metrics.setAccumulatedWatt(metrics.getAccumulatedWatt().add(event.getCurrentWatt()));

        boolean penaltyJustActivated = false;
        if (!penaltyWasActive && metrics.getAccumulatedCost().compareTo(billing.getBudgetQuota()) >= 0) {
            metrics.setPenaltyActive(true);
            penaltyJustActivated = true;
            log.warn("PENALTY TARIFF ACTIVATED for homeId={}: accumulatedCost={} >= budgetQuota={}",
                    event.getHomeId(), metrics.getAccumulatedCost(), billing.getBudgetQuota());
        }

        Long applianceId = event.getApplianceId();
        boolean isBreach = event.getCurrentWatt().compareTo(appliance.getSafeLimitWatt()) > 0;
        boolean anomalyWasActive = metrics.getApplianceAnomalyFlags().getOrDefault(applianceId, false);
        boolean anomalyJustDetected = false;
        int currentBreachCount = metrics.getApplianceBreachCounters().getOrDefault(applianceId, 0);

        if (isBreach) {
            currentBreachCount += 1;
            metrics.getApplianceBreachCounters().put(applianceId, currentBreachCount);

            if (!anomalyWasActive && currentBreachCount >= BREACH_THRESHOLD) {
                metrics.getApplianceAnomalyFlags().put(applianceId, true);
                anomalyJustDetected = true;
                log.warn("ANOMALY DETECTED: applianceId={} (homeId={}) breached safe limit {} times consecutively",
                        applianceId, event.getHomeId(), currentBreachCount);
            }
        } else {
            metrics.getApplianceBreachCounters().put(applianceId, 0);
            metrics.getApplianceAnomalyFlags().put(applianceId, false);
        }

        cache.put(event.getHomeId(), metrics);

        log.info("Telemetry processed: homeId={}, applianceId={}, watt={}, accumulatedCost={}, penaltyActive={}, breachCount={}",
                event.getHomeId(), applianceId, event.getCurrentWatt(), metrics.getAccumulatedCost(),
                metrics.isPenaltyActive(), currentBreachCount);

        if (penaltyJustActivated) {
            aiNotificationService.notifyPenaltyActivated(event.getHomeId(), billing, metrics.getAccumulatedCost());
        }
        if (anomalyJustDetected) {
            aiNotificationService.notifyAnomalyDetected(event.getHomeId(), appliance, currentBreachCount);
        }
    }
}