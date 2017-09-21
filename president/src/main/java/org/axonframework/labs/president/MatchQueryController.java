package org.axonframework.labs.president;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchQueryController {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchQueryController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping("/{matchId}")
    public MatchView getMatchView(@PathVariable String matchId) {
        return matchRepository.getOne(matchId);
    }

}
