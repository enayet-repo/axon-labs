package org.axonframework.labs.president;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MatchEventHandler {

    public static final Logger logger = LoggerFactory.getLogger(MatchEventHandler.class);

    @EventHandler
    public void on(MatchCreatedEvent event) {
        logger.info("Handling event {}", event);
    }

    @EventHandler
    public void on(MatchJoinedEvent event) {
        logger.info("Handling event {}", event);
    }

    @EventHandler
    public void on(GameStartedEvent event) {
        logger.info("Handling event {}", event);
    }

    @EventHandler
    public void on(CardsPlayedEvent event) {
        logger.info("Handling event {}", event);
    }

    @EventHandler
    public void on(PlayerPassedEvent event) {
        logger.info("Handling event {}", event);
    }

    @EventHandler
    public void on(GameEndedEvent event) {
        logger.info("Handling event {}", event);
    }

    @EventHandler
    public void on(MatchEndedEvent event) {
        logger.info("Handling event {}", event);
    }

}
