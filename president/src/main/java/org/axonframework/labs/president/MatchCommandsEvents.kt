package org.axonframework.labs.president

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateMatchCommand(
        @TargetAggregateIdentifier val aggregateIdentifier: String
)
data class JoinMatchCommand(
        @TargetAggregateIdentifier val aggregateIdentifier: String
)
data class StartMatchCommand(
        @TargetAggregateIdentifier val aggregateIdentifier: String
)
data class PlayCardsCommand(
        @TargetAggregateIdentifier val aggregateIdentifier: String
)
data class PassCommand(
        @TargetAggregateIdentifier val aggregateIdentifier: String
)

data class MatchCreatedEvent(
        val aggregateIdentifier: String
)
data class MatchJoinedEvent(
        val aggregateIdentifier: String
)
data class GameStartedEvent(
        val aggregateIdentifier: String
)
data class CardsPlayedEvent(
        val aggregateIdentifier: String
)
data class PlayerPassedEvent(
        val aggregateIdentifier: String
)
data class GameEndedEvent(
        val aggregateIdentifier: String
)
data class MatchEndedEvent(
        val aggregateIdentifier: String
)