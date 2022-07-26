package pl.edu.agh.DroneRadar.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import pl.edu.agh.DroneRadar.dto.DroneBasicDataDto;
import pl.edu.agh.DroneRadar.dto.FiltersDto;
import pl.edu.agh.DroneRadar.dto.MapObjectInfoDto;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.agh.DroneRadar.systemCache.CaffeineSystemCache;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@EnableScheduling
@Controller
public class SocketCommunicationController {
    private static SimpMessageSendingOperations messageSender;
    private final SystemCache systemCache;
    private final Map<String, FiltersDto> clients;

    public SocketCommunicationController(SimpMessageSendingOperations messageSender, SystemCache systemCache) {
        SocketCommunicationController.messageSender = messageSender;
        this.systemCache = systemCache;
        this.clients = new HashMap<>();
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {

        var objects = systemCache.getLatestEntries();
        var x = objects.toArray();
        this.clients.forEach((clientId, filters) -> {
            new Thread(() -> {
                var toSend = objects.stream()
                        .filter(entry -> entry.doesMeetFilters(filters))
                        .map(dto -> new MapObjectInfoDto(dto.getRegistrationNumber(),
                                dto.getLatitude(),
                                dto.getLongitude(),
                                dto.getRegistrationNumber(),
                                dto.getHeading()))
                        .collect(Collectors.toList());
                messageSender.convertAndSend("/client/" + clientId + "/map-data", toSend);
            }).start();
        });
    }

    @MessageMapping("/register")
    public void onClientRegister(String clientId) {
        this.clients.put(clientId, new FiltersDto());
    }

    @MessageMapping("/{clientId}/apply-filters")
    public void onApplyFilters(FiltersDto filters, @DestinationVariable String clientId) {
        this.clients.put(clientId, filters);
    }

    @MessageMapping("/{clientId}/reset-filters")
    public void onResetFilters(@DestinationVariable String clientId) {
        this.clients.put(clientId, new FiltersDto());
    }
}