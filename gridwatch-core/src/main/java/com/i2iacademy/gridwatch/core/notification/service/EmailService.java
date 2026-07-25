package com.i2iacademy.gridwatch.core.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    public void sendAdvisoryEmail(String toEmail, String homeName, String advisoryText) {
        log.info("=== EMAIL DISPATCH (simulated) ===");
        log.info("To: {}", toEmail);
        log.info("Subject: GridWatch Tasarruf Önerisi - {}", homeName);
        log.info("Body: {}", advisoryText);
        log.info("===================================");
    }
}