package com.i2iacademy.gridwatch.core.home.repository;

import com.i2iacademy.gridwatch.core.home.entity.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplianceRepository extends JpaRepository<Appliance, Long> {
    List<Appliance> findByHomeId(Long homeId);
}