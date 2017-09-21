package org.axonframework.labs.president

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class MatchView @JvmOverloads constructor(
        @Id val matchId: String,
        var gameId: String? = null,
        var matchName: String,
        var gameStatus: Status = Status.PENDING,
        var matchStatus: Status = Status.PENDING,
        var playerNames: MutableList<String> = mutableListOf()
)

enum class Status {
    PENDING, STARTED, FINISHED
}