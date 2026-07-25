package com.i2iacademy.gridwatch.core.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GeminiRequest {
    private List<Content> contents;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    public static GeminiRequest of(String prompt) {
        return new GeminiRequest(List.of(new Content(List.of(new Part(prompt)))));
    }
}