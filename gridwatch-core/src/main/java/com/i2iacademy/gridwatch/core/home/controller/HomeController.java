package com.i2iacademy.gridwatch.core.home.controller;

import com.i2iacademy.gridwatch.core.home.dto.HomeRegistrationRequest;
import com.i2iacademy.gridwatch.core.home.dto.HomeResponse;
import com.i2iacademy.gridwatch.core.home.entity.Appliance;
import com.i2iacademy.gridwatch.core.home.entity.Home;
import com.i2iacademy.gridwatch.core.home.repository.ApplianceRepository;
import com.i2iacademy.gridwatch.core.home.repository.HomeRepository;
import com.i2iacademy.gridwatch.core.home.service.HomeRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
@Tag(name = "Home Management", description = "Ev ve cihaz kayıt işlemleri")
public class HomeController {

    private final HomeRegistrationService homeRegistrationService;
    private final HomeRepository homeRepository;
    private final ApplianceRepository applianceRepository;

    @PostMapping
    @Operation(summary = "Yeni ev ve cihazlarını kaydet, Kafka'ya asset registration event'i yayınla")
    public ResponseEntity<HomeResponse> register(@Valid @RequestBody HomeRegistrationRequest request) {
        HomeResponse response = homeRegistrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Kayıtlı tüm evleri listele")
    public ResponseEntity<List<HomeResponse>> listHomes() {
        List<HomeResponse> homes = homeRepository.findAll().stream()
                .map(h -> new HomeResponse(h.getId(), h.getName(), h.getContactEmail()))
                .toList();
        return ResponseEntity.ok(homes);
    }

    @GetMapping("/{homeId}/appliances")
    @Operation(summary = "Bir evin cihazlarını listele")
    public ResponseEntity<List<Appliance>> listAppliances(@PathVariable Long homeId) {
        return ResponseEntity.ok(applianceRepository.findByHomeId(homeId));
    }
}