package com.i2iacademy.gridwatch.core.notification.service;

import com.i2iacademy.gridwatch.core.notification.dto.GeminiRequest;
import com.i2iacademy.gridwatch.core.notification.dto.GeminiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class GeminiClientService {

    private static final String MODEL = "gemini-flash-latest";

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    public String generateAdvisory(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + MODEL + ":generateContent?key=" + apiKey;

        try {
            GeminiResponse response = restClient.post()
                    .uri(url)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(GeminiRequest.of(prompt))
                    .retrieve()
                    .body(GeminiResponse.class);

            String text = response != null ? response.extractText() : null;
            if (text == null) {
                log.warn("Gemini returned empty response");
                return "Şu anda öneri oluşturulamadı.";
            }
            return text;
        } catch (Exception e) {
            log.error("Gemini API call failed: {}", e.getMessage());
            return "Şu anda öneri oluşturulamadı.";
        }
    }
}