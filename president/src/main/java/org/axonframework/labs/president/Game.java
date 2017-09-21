package org.axonframework.labs.president;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.EntityId;

public class Game {

    private final String matchId;
    @EntityId
    private final String gameId;

    public Game(String matchId, String gameId) {
        this.matchId = matchId;
        this.gameId = gameId;
    }

    @CommandHandler
    public void handle(PlayCardsCommand cmd) {
        apply(new CardsPlayedEvent(matchId, gameId));
    }

    @CommandHandler
    public void handle(PassCommand cmd) {
        apply(new PlayerPassedEvent(matchId, gameId));
    }

}
