package pl.edu.agh.DroneRadar.controller;

import com.droneradar.droneradarbackend.model.MapObjectInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.agh.DroneRadar.cache.SystemCacheService;

import java.util.*;


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
                .map(dto -> new MapObjectInfoDto((int) System.currentTimeMillis(),
                        dto.record().getFlightDataEntry().getCoordinate().getLatitude(),
                        dto.record().getFlightDataEntry().getCoordinate().getLongitude(),
                        dto.droneIdentification().toString()))
                .toArray();
        messageSender.convertAndSend("/client/map-data", objects);
    }
}