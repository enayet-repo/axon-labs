package org.axonframework.labs.president

data class TournamentCreatedEvent(val tournamentId: String)
data class TournamentStartedEvent(val tournamentId: String)