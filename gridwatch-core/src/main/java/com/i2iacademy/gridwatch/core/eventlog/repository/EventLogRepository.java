package com.i2iacademy.gridwatch.core.eventlog.repository;

import com.i2iacademy.gridwatch.core.eventlog.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    List<EventLog> findByHomeIdOrderByCreatedAtDesc(Long homeId);
}