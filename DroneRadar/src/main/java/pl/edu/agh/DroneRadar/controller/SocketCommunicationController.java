package pl.edu.agh.DroneRadar.controller;

import pl.edu.agh.DroneRadar.dto.MapObjectInfoDto;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.agh.DroneRadar.systemCache.CaffeineSystemCache;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;

import java.util.List;
@EnableScheduling
@Controller
public class SocketCommunicationController {
    private static SimpMessageSendingOperations messageSender;
    private final SystemCache systemCache;
    public SocketCommunicationController(SimpMessageSendingOperations messageSender, SystemCache systemCache) {
        SocketCommunicationController.messageSender = messageSender;
        this.systemCache = systemCache;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {
        var objects = systemCache.getLatestEntries()
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