package org.axonframework.labs.president

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateTournamentCommand(@TargetAggregateIdentifier val tournamentId: String)
data class StartTournamentCommand(@TargetAggregateIdentifier val tournamentId: String)

data class TournamentCreatedEvent(val tournamentId: String)
data class TournamentStartedEvent(val tournamentId: String)