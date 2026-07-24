package com.i2iacademy.gridwatch.core.analytics.repository;

import com.i2iacademy.gridwatch.core.analytics.entity.ConsumptionSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsumptionSnapshotRepository extends JpaRepository<ConsumptionSnapshot, Long> {
    List<ConsumptionSnapshot> findByHomeIdOrderBySnapshotDateAsc(Long homeId);
    Optional<ConsumptionSnapshot> findByHomeIdAndSnapshotDate(Long homeId, LocalDate snapshotDate);
}