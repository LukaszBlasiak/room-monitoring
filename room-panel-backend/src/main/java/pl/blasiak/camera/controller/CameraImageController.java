package pl.blasiak.camera.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class CameraImageController {

    private final CameraImageDispatcher cameraImageDispatcher;
    public static final String ROOM_PREVIEW_SEND_URL = "/preview";

    public CameraImageController(final CameraImageDispatcher cameraImageDispatcher) {
        this.cameraImageDispatcher = cameraImageDispatcher;
    }

    @MessageMapping(ROOM_PREVIEW_SEND_URL + "/start")
    public void startRoomPreview(StompHeaderAccessor stompHeaderAccessor) {
        cameraImageDispatcher.addNewSession(stompHeaderAccessor.getSessionId());
    }

    @MessageMapping(ROOM_PREVIEW_SEND_URL + "/stop")
    public void stopRoomPreview(StompHeaderAccessor stompHeaderAccessor) {
        cameraImageDispatcher.removeSession(stompHeaderAccessor.getSessionId());
    }

}
