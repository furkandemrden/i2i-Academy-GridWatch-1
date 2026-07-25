package com.i2iacademy.gridwatch.core.notification.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiResponse {
    private List<Candidate> candidates;

    @Getter
    @Setter
    public static class Candidate {
        private Content content;
    }

    @Getter
    @Setter
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @Setter
    public static class Part {
        private String text;
    }

    public String extractText() {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        return candidates.get(0).getContent().getParts().get(0).getText();
    }
}