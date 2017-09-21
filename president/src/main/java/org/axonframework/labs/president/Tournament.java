package org.axonframework.labs.president;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Tournament {

    @AggregateIdentifier
    private String tournamentId;

    public Tournament() {
        // Required by Axon
    }

    @CommandHandler
    public Tournament(CreateTournamentCommand cmd) {
        apply(new TournamentCreatedEvent(cmd.getTournamentId()));
    }

    @CommandHandler
    public void handle(StartTournamentCommand cmd) {
        apply(new TournamentStartedEvent(tournamentId));
    }

    @EventSourcingHandler
    public void on(TournamentCreatedEvent event) {
        tournamentId = event.getTournamentId();
    }

}
