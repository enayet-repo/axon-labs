package org.axonframework.labs.president;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Match {

    @AggregateIdentifier
    private String aggregateIdentifier;

    public Match() {
        // Required by Axon
    }

    public Match(CreateMatchCommand command) {
        apply(new MatchCreatedEvent(command.getAggregateIdentifier()));
    }

    public void handle(JoinMatchCommand cmd) {
        apply(new MatchJoinedEvent(cmd.getAggregateIdentifier()));
    }

    public void handle(StartMatchCommand cmd) {
        apply(new GameStartedEvent(cmd.getAggregateIdentifier()));
    }

    public void handle(PlayCardsCommand cmd) {
        apply(new CardsPlayedEvent(cmd.getAggregateIdentifier()));
    }

    public void handle(PassCommand cmd) {
        apply(new PlayerPassedEvent(cmd.getAggregateIdentifier()));
    }

}
