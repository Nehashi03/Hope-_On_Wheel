package com.hopeonwheel.backend.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TrackingHub {
    private final Map<Long, Map<Long, SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long bookingId){
        SseEmitter emitter = new SseEmitter(0L);
        long id = System.nanoTime();
        emitters.computeIfAbsent(bookingId, k -> new ConcurrentHashMap<>()).put(id, emitter);
        emitter.onCompletion(() -> emitters.getOrDefault(bookingId, Map.of()).remove(id));
        emitter.onTimeout(() -> emitters.getOrDefault(bookingId, Map.of()).remove(id));
        return emitter;
    }

    public void send(Long bookingId, Object payload){
        Map<Long, SseEmitter> group = emitters.get(bookingId);
        if (group == null) return;
        group.forEach((id, emitter) -> {
            try { emitter.send(payload); }
            catch (IOException e) { emitter.complete(); }
        });
    }
}
