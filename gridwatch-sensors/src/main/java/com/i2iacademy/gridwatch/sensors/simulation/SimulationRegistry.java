package com.i2iacademy.gridwatch.sensors.simulation;

import com.i2iacademy.gridwatch.sensors.messaging.ApplianceInfo;
import com.i2iacademy.gridwatch.sensors.messaging.HomeRegistrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class SimulationRegistry {

    private final List<SimulatedAppliance> appliances = new CopyOnWriteArrayList<>();

    public void registerHome(HomeRegistrationEvent event) {
        for (ApplianceInfo info : event.getAppliances()) {
            appliances.add(new SimulatedAppliance(
                    info.getApplianceId(),
                    event.getHomeId(),
                    info.getName(),
                    info.getSafeLimitWatt()
            ));
        }
        log.info("Registry updated: home {} added with {} appliances, total tracked appliances = {}",
                event.getHomeId(), event.getAppliances().size(), appliances.size());
    }

    public List<SimulatedAppliance> getAllAppliances() {
        return appliances;
    }
}