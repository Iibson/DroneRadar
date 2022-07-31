package pl.edu.agh.DroneRadar.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.agh.DroneRadar.dto.FilterDto;
import pl.edu.agh.DroneRadar.dto.MapObjectInfoDto;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.threadPool.AppThreadPool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableScheduling
@Controller

public class SocketCommunicationController {
    private static SimpMessageSendingOperations messageSender;
    private final SystemCache systemCache;
    private final Map<String, List<FilterDto>> clients;
    private final AppThreadPool pool;

    public SocketCommunicationController(SimpMessageSendingOperations messageSender, SystemCache systemCache, AppThreadPool pool) {
        SocketCommunicationController.messageSender = messageSender;
        this.systemCache = systemCache;
        this.clients = new HashMap<>();
        this.pool = pool;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {

        var objects = systemCache.getLatestEntries();
        var x = objects.toArray();
        this.clients.forEach((clientId, filters) -> {
            this.pool.submit(() -> {
                var toSend = objects.stream()
                        .filter(entry -> entry.doesMeetFilters(filters))
                        .map(dto -> new MapObjectInfoDto(dto.getRegistrationNumber(),
                                dto.getLatitude(),
                                dto.getLongitude(),
                                dto.getRegistrationNumber(),
                                dto.getHeading()))
                        .collect(Collectors.toList());
                messageSender.convertAndSend("/client/" + clientId + "/map-data", toSend);
            });
        });
    }

    @MessageMapping("/register")
    public void onClientRegister(String clientId) {
        this.clients.put(clientId, new LinkedList<>());
    }

    @MessageMapping("/{clientId}/apply-filters")
    public void onApplyFilters(List<FilterDto> filters, @DestinationVariable String clientId) {
        this.clients.put(clientId, filters);
    }

    @MessageMapping("/{clientId}/reset-filters")
    public void onResetFilters(@DestinationVariable String clientId) {
        this.clients.put(clientId, new LinkedList<>());
    }
}