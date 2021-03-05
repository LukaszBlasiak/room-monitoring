package pl.blasiak.camera.controller;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.blasiak.camera.model.ImageModel;
import pl.blasiak.camera.util.PiCameraUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CameraImageDispatcher {

    private final SimpMessagingTemplate template;
    private final Set<String> listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final PiCameraUtil piCameraUtil;

    public CameraImageDispatcher(final SimpMessagingTemplate template, final PiCameraUtil piCameraUtil) {
        this.template = template;
        this.piCameraUtil = piCameraUtil;
    }

    public void add(final String sessionId) {
        listeners.add(sessionId);
    }

    public void remove(final String sessionId) {
        listeners.remove(sessionId);
    }

    @Scheduled(fixedDelay = 2000)
    public void dispatch() {
        for (final String listener : listeners) {
            var headerAccessor = this.prepareHeaderAccessor(listener);
            final ImageModel image = piCameraUtil.getCameraImage();
            template.convertAndSendToUser(
                    listener,
                    CameraImageController.MEDIUM_ROOM_PREVIEW_SEND_URL,
                    image,
                    headerAccessor.getMessageHeaders());
        }
    }

    private SimpMessageHeaderAccessor prepareHeaderAccessor(final String listener) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(listener);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor;
    }

    @EventListener
    public void sessionDisconnectionHandler(final SessionDisconnectEvent event) {
        remove(event.getSessionId());
    }
}
