package com.i2iacademy.gridwatch.core.home.controller;

import com.i2iacademy.gridwatch.core.home.dto.HomeRegistrationRequest;
import com.i2iacademy.gridwatch.core.home.dto.HomeResponse;
import com.i2iacademy.gridwatch.core.home.service.HomeRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
@Tag(name = "Home Management", description = "Ev ve cihaz kayıt işlemleri")
public class HomeController {

    private final HomeRegistrationService homeRegistrationService;

    @PostMapping
    @Operation(summary = "Yeni ev ve cihazlarını kaydet, Kafka'ya asset registration event'i yayınla")
    public ResponseEntity<HomeResponse> register(@Valid @RequestBody HomeRegistrationRequest request) {
        HomeResponse response = homeRegistrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}