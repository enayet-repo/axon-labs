package org.axonframework.labs.president

data class MatchView(
        val matchId: String,
        val gameId: String,
        val matchName: String,
        val playerNames: MutableList<String>
)