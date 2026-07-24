package com.i2iacademy.gridwatch.core.home.service;

import com.i2iacademy.gridwatch.core.billing.entity.BillingAccount;
import com.i2iacademy.gridwatch.core.billing.repository.BillingAccountRepository;
import com.i2iacademy.gridwatch.core.home.dto.ApplianceRequest;
import com.i2iacademy.gridwatch.core.home.dto.HomeRegistrationRequest;
import com.i2iacademy.gridwatch.core.home.dto.HomeResponse;
import com.i2iacademy.gridwatch.core.home.entity.Appliance;
import com.i2iacademy.gridwatch.core.home.entity.Home;
import com.i2iacademy.gridwatch.core.home.repository.ApplianceRepository;
import com.i2iacademy.gridwatch.core.home.repository.HomeRepository;
import com.i2iacademy.gridwatch.core.messaging.HomeRegistrationEvent;
import com.i2iacademy.gridwatch.core.messaging.HomeRegistrationProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeRegistrationService {

    private final HomeRepository homeRepository;
    private final ApplianceRepository applianceRepository;
    private final BillingAccountRepository billingAccountRepository;
    private final HomeRegistrationProducerService producerService;

    @Transactional
    public HomeResponse register(HomeRegistrationRequest request) {
        Home home = new Home();
        home.setName(request.getName());
        home.setContactEmail(request.getContactEmail());
        Home savedHome = homeRepository.save(home);

        for (ApplianceRequest applianceRequest : request.getAppliances()) {
            Appliance appliance = new Appliance();
            appliance.setHome(savedHome);
            appliance.setName(applianceRequest.getName());
            appliance.setSafeLimitWatt(applianceRequest.getSafeLimitWatt());
            applianceRepository.save(appliance);
        }

        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setHome(savedHome);
        billingAccount.setBudgetQuota(request.getBudgetQuota());
        billingAccount.setNormalRate(request.getNormalRate());
        billingAccount.setPenaltyRate(request.getPenaltyRate());
        billingAccount.setPenaltyActive(false);
        billingAccountRepository.save(billingAccount);

        producerService.publish(new HomeRegistrationEvent(savedHome.getId(), savedHome.getName()));

        return new HomeResponse(savedHome.getId(), savedHome.getName(), savedHome.getContactEmail());
    }
}