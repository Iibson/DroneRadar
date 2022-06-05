package pl.edu.agh.DroneRadar.controller;

import pl.edu.agh.DroneRadar.dto.MapObjectInfoDto;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.agh.DroneRadar.cache.SystemCacheService;


/**
 * Controller responsible for websocket communication while inside a game
 */
@EnableScheduling
@Controller
public class SocketCommunicationController {
    private static SimpMessageSendingOperations messageSender;
    private final SystemCacheService cacheService;

    public SocketCommunicationController(SimpMessageSendingOperations messageSender, SystemCacheService cacheService) {
        SocketCommunicationController.messageSender = messageSender;
        this.cacheService = cacheService;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {
        var objects = cacheService.getLatestRecordsForDrones()
                .stream()
                .map(dto -> new MapObjectInfoDto(dto.droneIdentification(),
                        dto.record().getFlightDataEntry().getCoordinate().getLatitude(),
                        dto.record().getFlightDataEntry().getCoordinate().getLongitude(),
                        dto.droneIdentification().toString(),
                        dto.angle()))
                .toArray();
        messageSender.convertAndSend("/client/map-data", objects);
    }
}