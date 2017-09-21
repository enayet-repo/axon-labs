package org.axonframework.labs.president

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateMatchCommand(
        @TargetAggregateIdentifier val matchId: String
)

data class JoinMatchCommand(
        @TargetAggregateIdentifier val matchId: String,
        val playerName: String
)

data class StartMatchCommand(
        @TargetAggregateIdentifier val matchId: String
)

data class PlayCardsCommand(
        @TargetAggregateIdentifier val matchId: String,
        val gameId: String
)

data class PassCommand(
        @TargetAggregateIdentifier val matchId: String,
        val gameId: String
)

data class MatchCreatedEvent(
        val matchId: String
)

data class MatchJoinedEvent(
        val matchId: String,
        val playerName: String
)

data class GameStartedEvent(
        val matchId: String,
        val gameId: String
)

data class CardsPlayedEvent(
        val matchId: String,
        val gameId: String
)

data class PlayerPassedEvent(
        val matchId: String,
        val gameId: String,
        val playerName: String
)

data class GameEndedEvent(
        val matchId: String,
        val gameId: String
)

data class MatchEndedEvent(
        val matchId: String
)