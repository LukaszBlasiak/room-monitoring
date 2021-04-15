package pl.blasiak.camera.controller;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.blasiak.camera.dto.ImageResponseModel;
import pl.blasiak.camera.util.PiCameraUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CameraImageDispatcher {

    private final SimpMessagingTemplate template;
    private final Set<String> cameraPreviewListeners = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final PiCameraUtil piCameraUtil;
    private final ReentrantLock sendImageMutex = new ReentrantLock();

    public CameraImageDispatcher(final SimpMessagingTemplate template, final PiCameraUtil piCameraUtil) {
        this.template = template;
        this.piCameraUtil = piCameraUtil;
    }

    @EventListener
    public void sessionDisconnectionHandler(final SessionDisconnectEvent event) {
        this.removeSession(event.getSessionId());
    }

    public void addNewSession(final String sessionId) {
        cameraPreviewListeners.add(sessionId);
    }

    public void removeSession(final String sessionId) {
        cameraPreviewListeners.remove(sessionId);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendNewCameraPreview() {
        if (this.previousScheduleStillSendingImage() || this.noOneIsListeningCameraPreview()) {
            return;
        }
        try {
            this.sendImageMutex.lock();
            final ImageResponseModel imageToSend = this.piCameraUtil.getCameraImage();
            this.sendSingleCameraPreviewToAllSubscribers(imageToSend);
        } finally {
            sendImageMutex.unlock();
        }
    }

    private boolean previousScheduleStillSendingImage() {
        return this.sendImageMutex.isLocked();
    }

    private boolean noOneIsListeningCameraPreview() {
        return this.cameraPreviewListeners.isEmpty();
    }

    private void sendSingleCameraPreviewToAllSubscribers(final ImageResponseModel imageToSend) {
        for (final String listener : cameraPreviewListeners) {
            final var headerAccessor = this.prepareHeaderAccessor(listener);
            this.template.convertAndSendToUser(
                    listener,
                    CameraImageController.MEDIUM_ROOM_PREVIEW_SEND_URL,
                    imageToSend,
                    headerAccessor.getMessageHeaders());
        }
    }

    private SimpMessageHeaderAccessor prepareHeaderAccessor(final String listener) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(listener);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor;
    }


}
