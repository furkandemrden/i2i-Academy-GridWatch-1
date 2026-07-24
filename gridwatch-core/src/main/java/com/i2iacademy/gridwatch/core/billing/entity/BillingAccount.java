package com.i2iacademy.gridwatch.core.billing.entity;

import com.i2iacademy.gridwatch.core.home.entity.Home;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "billing_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BillingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id", nullable = false, unique = true)
    private Home home;

    @Column(name = "budget_quota", nullable = false, precision = 12, scale = 2)
    private BigDecimal budgetQuota;

    @Column(name = "normal_rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal normalRate;

    @Column(name = "penalty_rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal penaltyRate;

    @Column(name = "is_penalty_active", nullable = false)
    private boolean penaltyActive = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }
}