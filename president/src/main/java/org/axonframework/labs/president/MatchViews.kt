package org.axonframework.labs.president

data class MatchView(
        val matchId: String,
        val gameId: String? = null,
        val matchName: String,
        val playerNames: MutableList<String> = mutableListOf()
)