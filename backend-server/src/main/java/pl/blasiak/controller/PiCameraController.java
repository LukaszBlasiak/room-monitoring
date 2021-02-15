package pl.blasiak.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.dispatcher.NotificationDispatcher;

@RestController()
public class PiCameraController {

    private final NotificationDispatcher notificationDispatcher;

    public PiCameraController(final NotificationDispatcher notificationDispatcher) {
        this.notificationDispatcher = notificationDispatcher;
    }


    @MessageMapping("/preview/medium/start")
    @SendTo("/preview/medium")
    public void startMediumRoomPreview(StompHeaderAccessor stompHeaderAccessor) {
        notificationDispatcher.add(stompHeaderAccessor.getSessionId());
    }

    @MessageMapping("/preview/medium/stop")
    public void stopMediumRoomPreview(StompHeaderAccessor stompHeaderAccessor) {
        notificationDispatcher.remove(stompHeaderAccessor.getSessionId());
    }
}
