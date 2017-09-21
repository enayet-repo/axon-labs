package org.axonframework.labs.president

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class MatchView @JvmOverloads constructor(
        @Id var matchId: String? = null,
        var gameId: String? = null,
        var matchName: String? = null,
        var gameStatus: Status = Status.PENDING,
        var matchStatus: Status = Status.PENDING,
        @ElementCollection var playerNames: MutableList<String> = mutableListOf()
)

enum class Status {
    PENDING, STARTED, FINISHED
}