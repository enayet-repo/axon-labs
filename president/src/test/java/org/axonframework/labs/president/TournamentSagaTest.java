package org.axonframework.labs.president;

import java.util.UUID;

import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Test;

public class TournamentSagaTest {

    private static final String MATCH_ID_ASSOCIATION_KEY = "matchId";

    private static final String TOURNAMENT_ID = UUID.randomUUID().toString();
    private static final String MATCH_ID_ONE = UUID.randomUUID().toString();
    private static final String MATCH_ID_TWO = UUID.randomUUID().toString();
    private static final String MATCH_ID_THREE = UUID.randomUUID().toString();
    private static final String MATCH_ID_FOUR = UUID.randomUUID().toString();

    private SagaTestFixture<TournamentSaga> fixture = new SagaTestFixture<>(TournamentSaga.class);

    @Test
    public void testOnTournamentCreatedEventASagaExists() throws Exception {
        fixture.givenNoPriorActivity()
               .whenAggregate(TOURNAMENT_ID)
               .publishes(new TournamentCreatedEvent(TOURNAMENT_ID))
               .expectActiveSagas(1);
    }

    @Test
    public void testOnTournamentStartedEventSagaPublishesFourMatchCreatedCommands() throws Exception {
        fixture.givenAggregate(TOURNAMENT_ID)
               .published(new TournamentCreatedEvent(TOURNAMENT_ID))
               .whenPublishingA(new TournamentStartedEvent(TOURNAMENT_ID))
               .expectActiveSagas(1)
               .expectDispatchedCommands(
                       new CreateMatchCommand(MATCH_ID_ONE, "matchOne"),
                       new CreateMatchCommand(MATCH_ID_TWO, "matchTwo"),
                       new CreateMatchCommand(MATCH_ID_THREE, "matchThree"),
                       new CreateMatchCommand(MATCH_ID_FOUR, "matchFour")
               )
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_ONE)
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_TWO)
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_THREE)
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_FOUR);
    }

    @Test
    public void testOnMatchEndedEventForAllMatchesSagaPublishesStartRoundCommand() throws Exception {
        fixture.givenAggregate(TOURNAMENT_ID)
               .published(new TournamentCreatedEvent(TOURNAMENT_ID))
               .andThenAPublished(new TournamentStartedEvent(TOURNAMENT_ID))
               .andThenAggregate(MATCH_ID_ONE)
               .published(new MatchEndedEvent(MATCH_ID_ONE))
               .andThenAggregate(MATCH_ID_TWO)
               .published(new MatchEndedEvent(MATCH_ID_TWO))
               .andThenAggregate(MATCH_ID_THREE)
               .published(new MatchEndedEvent(MATCH_ID_THREE))
               .whenAggregate(MATCH_ID_FOUR)
               .publishes(new MatchEndedEvent(MATCH_ID_FOUR))
               .expectActiveSagas(1)
               .expectDispatchedCommands(new StartRoundCommand(TOURNAMENT_ID));
    }

    @Test
    public void testOnRoundStartedEventSagaPublishedFourStartMatchCommands() throws Exception {
        fixture.givenAggregate(TOURNAMENT_ID)
               .published(new TournamentCreatedEvent(TOURNAMENT_ID))
               .andThenAPublished(new TournamentStartedEvent(TOURNAMENT_ID))
               .whenAggregate(TOURNAMENT_ID)
               .publishes(new RoundStartedEvent(TOURNAMENT_ID))
               .expectActiveSagas(1)
               .expectDispatchedCommands(
                       new CreateMatchCommand(MATCH_ID_ONE, "matchOne"),
                       new CreateMatchCommand(MATCH_ID_TWO, "matchTwo"),
                       new CreateMatchCommand(MATCH_ID_THREE, "matchThree"),
                       new CreateMatchCommand(MATCH_ID_FOUR, "matchFour")
               )
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_ONE)
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_TWO)
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_THREE)
               .expectAssociationWith(MATCH_ID_ASSOCIATION_KEY, MATCH_ID_FOUR);
    }

    @Test
    public void testOnTournamentFinishedEventSagaEnds() throws Exception {
        fixture.givenAggregate(TOURNAMENT_ID)
               .published(new TournamentCreatedEvent(TOURNAMENT_ID))
               .andThenAPublished(new TournamentStartedEvent(TOURNAMENT_ID))
               .whenAggregate(TOURNAMENT_ID)
               .publishes(new TournamentFinishedEvent(TOURNAMENT_ID))
               .expectActiveSagas(0)
               .expectNoDispatchedCommands();
    }
}
