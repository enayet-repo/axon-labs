package org.axonframework.labs.president;

import java.io.IOException;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class MatchEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(MatchEventHandler.class);

    private final SseEmitter sseEmitter;

    @Autowired
    public MatchEventHandler(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    @EventHandler
    public void on(MatchCreatedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

    @EventHandler
    public void on(MatchJoinedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

    @EventHandler
    public void on(GameStartedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

    @EventHandler
    public void on(CardsPlayedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

    @EventHandler
    public void on(PlayerPassedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

    @EventHandler
    public void on(GameEndedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

    @EventHandler
    public void on(MatchEndedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());
    }

}
