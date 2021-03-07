package pl.blasiak.camera.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;


@RestController()
public class CameraImageController {

    private final CameraImageDispatcher cameraImageDispatcher;
    public static final String MEDIUM_ROOM_PREVIEW_SEND_URL = "/preview/medium";

    public CameraImageController(final CameraImageDispatcher cameraImageDispatcher) {
        this.cameraImageDispatcher = cameraImageDispatcher;
    }


    @MessageMapping(MEDIUM_ROOM_PREVIEW_SEND_URL + "/start")
    public void startMediumRoomPreview(StompHeaderAccessor stompHeaderAccessor) {
        cameraImageDispatcher.addNewSession(stompHeaderAccessor.getSessionId());
    }

    @MessageMapping(MEDIUM_ROOM_PREVIEW_SEND_URL + "/stop")
    public void stopMediumRoomPreview(StompHeaderAccessor stompHeaderAccessor) {
        cameraImageDispatcher.removeSession(stompHeaderAccessor.getSessionId());
    }

}
