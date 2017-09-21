package org.axonframework.labs.president

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class StartRoundCommand(@TargetAggregateIdentifier val tournamentId: String)

data class TournamentCreatedEvent(val tournamentId: String)
data class TournamentStartedEvent(val tournamentId: String)
data class RoundStartedEvent(val tournamentId: String)
data class TournamentFinishedEvent(val tournamentId: String)