package org.axonframework.labs.president;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Match {

    @AggregateIdentifier
    private String aggregateIdentifier;

    public Match() {
        // Required by Axon
    }

    @CommandHandler
    public Match(CreateMatchCommand command) {
        apply(new MatchCreatedEvent(command.getAggregateIdentifier()));
    }

    @CommandHandler
    public void handle(JoinMatchCommand cmd) {
        apply(new MatchJoinedEvent(cmd.getAggregateIdentifier()));
    }

    @CommandHandler
    public void handle(StartMatchCommand cmd) {
        apply(new GameStartedEvent(cmd.getAggregateIdentifier()));
    }

    @CommandHandler
    public void handle(PlayCardsCommand cmd) {
        apply(new CardsPlayedEvent(cmd.getAggregateIdentifier()));
    }

    @CommandHandler
    public void handle(PassCommand cmd) {
        apply(new PlayerPassedEvent(cmd.getAggregateIdentifier()));
    }

    @EventSourcingHandler
    public void on(MatchCreatedEvent event) {
        aggregateIdentifier = event.getAggregateIdentifier();
    }

}
