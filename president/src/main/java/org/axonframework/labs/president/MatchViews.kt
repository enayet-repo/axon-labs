package org.axonframework.labs.president

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class MatchView @JvmOverloads constructor(
        @Id val matchId: String,
        var gameId: String? = null,
        var matchName: String,
        var gameStatus: GameStatus = GameStatus.PENDING,
        var playerNames: MutableList<String> = mutableListOf()
)

enum class GameStatus {
    PENDING, STARTED, FINISHED
}