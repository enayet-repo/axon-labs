package org.axonframework.labs.president;

import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;
import static org.axonframework.eventhandling.saga.SagaLifecycle.removeAssociationWith;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class TournamentSaga {

    static final String TOURNAMENT_ID_ASSOCIATION_KEY = "tournamentId";
    static final String MATCH_ID_ASSOCIATION_KEY = "matchId";
    static final Duration MATCH_DEADLINE = Duration.of(10, ChronoUnit.MINUTES);

    private static final Integer NUMBER_OF_MATCHES = 4;

    private transient CommandGateway commandGateway;
    private transient IdGenerator idGenerator;
    private transient EventScheduler eventScheduler;

    private String tournamentId;
    private Integer numberOfRunningMatches;

    @StartSaga
    @SagaEventHandler(associationProperty = TOURNAMENT_ID_ASSOCIATION_KEY)
    public void on(TournamentCreatedEvent event) {
        tournamentId = event.getTournamentId();
        numberOfRunningMatches = 0;
    }

    @SagaEventHandler(associationProperty = TOURNAMENT_ID_ASSOCIATION_KEY)
    public void on(TournamentStartedEvent event) {
        startNumberOfMatches(NUMBER_OF_MATCHES);
    }

    @SagaEventHandler(associationProperty = MATCH_ID_ASSOCIATION_KEY)
    public void on(MatchEndedEvent event) {
        removeAssociationWith(MATCH_ID_ASSOCIATION_KEY, event.getMatchId());
        numberOfRunningMatches--;
        if (numberOfRunningMatches == 0) {
            commandGateway.send(new StartRoundCommand(tournamentId));
        }
    }

    @SagaEventHandler(associationProperty = TOURNAMENT_ID_ASSOCIATION_KEY)
    public void on(RoundStartedEvent event) {
        startNumberOfMatches(NUMBER_OF_MATCHES);
    }

    private void startNumberOfMatches(Integer numberOfMatches) {
        numberOfRunningMatches = numberOfMatches;

        for (int i = 0; i < numberOfMatches; i++) {
            String matchId = idGenerator.generateId();
            associateWith(MATCH_ID_ASSOCIATION_KEY, matchId);
            commandGateway.send(new CreateMatchCommand(matchId, createMatchName(i)));
        }

        eventScheduler.schedule(MATCH_DEADLINE, new MatchTimeDeadLineReachedEvent(tournamentId));
    }

    private String createMatchName(int matchNumber) {
        return "match" + matchNumber;
    }

    @EndSaga
    @SagaEventHandler(associationProperty = TOURNAMENT_ID_ASSOCIATION_KEY)
    public void on(TournamentFinishedEvent event) {
        // Do nothing, as this ends the saga
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Autowired
    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Autowired
    public void setEventScheduler(EventScheduler eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

}
