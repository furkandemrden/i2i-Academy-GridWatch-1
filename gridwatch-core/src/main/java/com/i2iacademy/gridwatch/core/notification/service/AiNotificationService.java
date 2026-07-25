package com.i2iacademy.gridwatch.core.notification.service;

import com.i2iacademy.gridwatch.core.billing.entity.BillingAccount;
import com.i2iacademy.gridwatch.core.home.entity.Appliance;
import com.i2iacademy.gridwatch.core.home.entity.Home;
import com.i2iacademy.gridwatch.core.home.repository.HomeRepository;
import com.i2iacademy.gridwatch.core.notification.entity.AiRecommendation;
import com.i2iacademy.gridwatch.core.notification.repository.AiRecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiNotificationService {

    private final GeminiClientService geminiClientService;
    private final EmailService emailService;
    private final HomeRepository homeRepository;
    private final AiRecommendationRepository aiRecommendationRepository;

    @Async
    public void notifyPenaltyActivated(Long homeId, BillingAccount billing, BigDecimal accumulatedCost) {
        Optional<Home> homeOpt = homeRepository.findById(homeId);
        if (homeOpt.isEmpty()) {
            return;
        }
        Home home = homeOpt.get();

        String prompt = String.format(
                "Sen bir enerji tasarrufu danışmanısın. \"%s\" adlı ev, %.2f birimlik bütçe kotasını aştı " +
                "ve şu an %.2f birim harcamış durumda, bu yüzden ceza tarifesine geçti. " +
                "Bu eve, enerji tüketimini azaltması için samimi, kısa (maksimum 4 cümle), " +
                "Türkçe ve teşvik edici bir tavsiye metni yaz. Rakamları tekrar etme, doğrudan davranışsal öneriler ver.",
                home.getName(), billing.getBudgetQuota(), accumulatedCost
        );

        String advisory = geminiClientService.generateAdvisory(prompt);
        persistAndSend(home, advisory);
    }

    @Async
    public void notifyAnomalyDetected(Long homeId, Appliance appliance, int breachCount) {
        Optional<Home> homeOpt = homeRepository.findById(homeId);
        if (homeOpt.isEmpty()) {
            return;
        }
        Home home = homeOpt.get();

        String prompt = String.format(
                "Sen bir enerji tasarrufu danışmanısın. \"%s\" adlı evde bulunan \"%s\" cihazı, " +
                "güvenli tüketim limitini üst üste %d kez aştı ve anomali olarak işaretlendi. " +
                "Bu eve, bu cihazla ilgili samimi, kısa (maksimum 4 cümle), Türkçe ve " +
                "teşvik edici bir tavsiye metni yaz. Cihazı kontrol etmesini veya kullanım alışkanlığını " +
                "gözden geçirmesini öner.",
                home.getName(), appliance.getName(), breachCount
        );

        String advisory = geminiClientService.generateAdvisory(prompt);
        persistAndSend(home, advisory);
    }

    private void persistAndSend(Home home, String advisory) {
        AiRecommendation recommendation = new AiRecommendation();
        recommendation.setHome(home);
        recommendation.setRecommendationText(advisory);
        aiRecommendationRepository.save(recommendation);

        emailService.sendAdvisoryEmail(home.getContactEmail(), home.getName(), advisory);

        log.info("AI recommendation persisted and email dispatched for homeId={}", home.getId());
    }
}