package pl.edu.agh.DroneRadar.controller;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
@EnableScheduling
@Controller
public class SocketCommunicationController {
    private static SimpMessageSendingOperations messageSender;
    public SocketCommunicationController(SimpMessageSendingOperations messageSender) {
        SocketCommunicationController.messageSender = messageSender;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendMapData() {
        var objects = List.of()
                .toArray();
        messageSender.convertAndSend("/client/map-data", objects);
    }
}