package org.axonframework.labs.president;

import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;

public class MatchTest {

    private static final String MATCH_ID = UUID.randomUUID().toString();
    private static final String GAME_ID = UUID.randomUUID().toString();
    private static final String MATCH_NAME = "MatchName";

    private FixtureConfiguration<Match> fixture = new AggregateTestFixture<>(Match.class);

    @Test
    public void testCreateMatch() throws Exception {
        fixture.givenNoPriorActivity()
               .when(new CreateMatchCommand(MATCH_ID, MATCH_NAME))
               .expectEvents(new MatchCreatedEvent(MATCH_ID, MATCH_NAME));
    }

    @Test
    public void testJoinMatch() throws Exception {
        fixture.given(new MatchCreatedEvent(MATCH_ID, MATCH_NAME))
               .when(new JoinMatchCommand(MATCH_ID, "Steven"))
               .expectEvents(new MatchJoinedEvent(MATCH_ID, "Steven"));
    }

    @Test
    public void testStartMatch() throws Exception {
        fixture.given(new MatchCreatedEvent(MATCH_ID, MATCH_NAME), new MatchJoinedEvent(MATCH_ID, "Steven"))
               .when(new StartMatchCommand(MATCH_ID, GAME_ID))
               .expectEvents(new GameStartedEvent(MATCH_ID, GAME_ID));
    }

    @Test
    public void testPlayCards() throws Exception {
        fixture.given(new MatchCreatedEvent(MATCH_ID, MATCH_NAME),
                      new MatchJoinedEvent(MATCH_ID, "Steven"),
                      new GameStartedEvent(MATCH_ID, GAME_ID))
               .when(new PlayCardsCommand(MATCH_ID, GAME_ID))
               .expectEvents(new CardsPlayedEvent(MATCH_ID, GAME_ID));
    }

    @Test
    public void testPass() throws Exception {
        fixture.given(new MatchCreatedEvent(MATCH_ID, MATCH_NAME),
                      new MatchJoinedEvent(MATCH_ID, "Steven"),
                      new GameStartedEvent(MATCH_ID, GAME_ID))
               .when(new PassCommand(MATCH_ID, GAME_ID, "Steven"))
               .expectEvents(new PlayerPassedEvent(MATCH_ID, GAME_ID, "Steven"));
    }

}