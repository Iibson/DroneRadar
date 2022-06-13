package pl.edu.agh.DroneRadar.controller;

import pl.edu.agh.DroneRadar.dto.MapObjectInfoDto;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.agh.DroneRadar.systemCache.CaffeineSystemCache;

import java.util.List;
@EnableScheduling
@Controller
public class SocketCommunicationController {
    private static SimpMessageSendingOperations messageSender;
    private final CaffeineSystemCache cacheService;
    public SocketCommunicationController(SimpMessageSendingOperations messageSender, CaffeineSystemCache cacheService) {
        SocketCommunicationController.messageSender = messageSender;
        this.cacheService = cacheService;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {
        var objects = cacheService.getLatestEntries()
                .stream()
                .map(dto -> new MapObjectInfoDto(dto.registrationNumber(),
                        dto.latitude(),
                        dto.longitude(),
                        dto.registrationNumber(),
                        dto.heading()))
                .toArray();
        messageSender.convertAndSend("/client/map-data", objects);
    }
}