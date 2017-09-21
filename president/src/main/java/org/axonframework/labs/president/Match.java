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
    public Match(CreateMatchCommand cmd) {
        apply(new MatchCreatedEvent(cmd.getMatchId(), cmd.getMatchName()));
        matchId = cmd.getMatchId();
    }

    @CommandHandler
    public void handle(JoinMatchCommand cmd) {
        apply(new MatchJoinedEvent(matchId, cmd.getPlayerName()));
    }

    @CommandHandler
    public void handle(StartMatchCommand cmd) {
        String gameId = UUID.randomUUID().toString();
        apply(new GameStartedEvent(matchId, gameId));
        game = new Game(matchId, cmd.getGameId());
    }

}
