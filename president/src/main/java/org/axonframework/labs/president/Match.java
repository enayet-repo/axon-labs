package org.axonframework.labs.president;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Entity
@Aggregate
public class Match {

    @Id
    @AggregateIdentifier
    private String matchId;
    @OneToOne(cascade = CascadeType.ALL)
    @AggregateMember
    private Game game;

    public Match() {
        // Required by Axon
    }

    @CommandHandler
    public Match(CreateMatchCommand command) {
        apply(new MatchCreatedEvent(command.getMatchId(), command.getMatchName()));
    }

    @CommandHandler
    public void handle(JoinMatchCommand cmd) {
        apply(new MatchJoinedEvent(matchId, cmd.getPlayerName()));
    }

    @CommandHandler
    public String handle(StartMatchCommand cmd) {
        String gameId = UUID.randomUUID().toString();
        apply(new GameStartedEvent(matchId, gameId));
        return gameId;
    }

    @EventSourcingHandler
    public void on(MatchCreatedEvent event) {
        matchId = event.getMatchId();
    }

    @EventSourcingHandler
    public void on(GameStartedEvent event) {
        game = new Game(matchId, event.getGameId());
    }

}
