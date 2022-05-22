package com.droneradar.droneradarbackend.controllers;

import com.droneradar.droneradarbackend.model.MapObjectInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;


/**
 * Controller responsible for websocket communication while inside a game
 */
@EnableScheduling
@Controller
public class SocketCommunicationController {

    private static SimpMessageSendingOperations messageSender;

    public SocketCommunicationController(SimpMessageSendingOperations messageSender) {
        SocketCommunicationController.messageSender = messageSender;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {
        List<MapObjectInfoDto> objects = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {

            MapObjectInfoDto object = new MapObjectInfoDto((int) System.currentTimeMillis(),
                    (random.nextInt(36000) - 18000)/100.0,
                    (random.nextInt(36000) - 18000)/100.0,
                    String.valueOf(System.currentTimeMillis()+i)
            );
            objects.add(object);
        }
        System.out.println(System.currentTimeMillis());
        messageSender.convertAndSend("/client/map-data", objects.toArray());
    }
}