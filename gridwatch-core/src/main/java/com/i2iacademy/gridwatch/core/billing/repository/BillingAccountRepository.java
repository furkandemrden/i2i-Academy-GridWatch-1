package com.i2iacademy.gridwatch.core.billing.repository;

import com.i2iacademy.gridwatch.core.billing.entity.BillingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingAccountRepository extends JpaRepository<BillingAccount, Long> {
    Optional<BillingAccount> findByHomeId(Long homeId);
}