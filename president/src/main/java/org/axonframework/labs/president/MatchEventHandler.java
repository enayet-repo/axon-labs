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
    private final MatchRepository matchRepository;

    @Autowired
    public MatchEventHandler(SseEmitter sseEmitter, MatchRepository matchRepository) {
        this.sseEmitter = sseEmitter;
        this.matchRepository = matchRepository;
    }

    @EventHandler
    public void on(MatchCreatedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());

        MatchView matchView = new MatchView(event.getMatchId(), event.getMatchId());
        matchRepository.save(matchView);
    }

    @EventHandler
    public void on(MatchJoinedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());

        MatchView matchView = matchRepository.getOne(event.getMatchId());
        matchView.getPlayerNames().add(event.getPlayerName());
        matchRepository.save(matchView);
    }

    @EventHandler
    public void on(GameStartedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());

        MatchView matchView = matchRepository.getOne(event.getMatchId());
        matchView.setGameStatus(Status.STARTED);
        matchView.setMatchStatus(Status.STARTED);
        matchRepository.save(matchView);
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

        MatchView matchView = matchRepository.getOne(event.getMatchId());
        matchView.setGameStatus(Status.FINISHED);
        matchRepository.save(matchView);
    }

    @EventHandler
    public void on(MatchEndedEvent event) throws IOException {
        logger.info("Handling event {}", event);
        sseEmitter.send(event.toString());

        MatchView matchView = matchRepository.getOne(event.getMatchId());
        matchView.setMatchStatus(Status.FINISHED);
        matchRepository.save(matchView);
    }

}
