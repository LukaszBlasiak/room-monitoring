package pl.blasiak.camera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.application.exception.CameraException;


@RestController("/api")
public class CameraImageController {

    private final CameraImageDispatcher cameraImageDispatcher;
    public static final String MEDIUM_ROOM_PREVIEW_SEND_URL = "/preview/medium";

    public CameraImageController(final CameraImageDispatcher cameraImageDispatcher) {
        this.cameraImageDispatcher = cameraImageDispatcher;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        throw new CameraException("a");
//        return ResponseEntity.ok("hello world");
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
