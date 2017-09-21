package org.axonframework.labs.president;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class TournamentController {

    private final CommandGateway commandGateway;
    private final SseEmitter sseEmitter;

    @Autowired
    public TournamentController(CommandGateway commandGateway, SseEmitter sseEmitter) {
        this.commandGateway = commandGateway;
        this.sseEmitter = sseEmitter;
    }

    @GetMapping("/full-tournament")
    public SseEmitter buildTournament() {
        String tournamentId = UUID.randomUUID().toString();
        commandGateway.send(new CreateTournamentCommand(tournamentId));
        commandGateway.send(new StartTournamentCommand(tournamentId));
        return sseEmitter;
    }

    @GetMapping("/create-tournament")
    public String createTournament() {
        String tournamentId = UUID.randomUUID().toString();
        commandGateway.send(new CreateTournamentCommand(tournamentId));
        return tournamentId;
    }

    @GetMapping("/start-tournament/{tournamentId}")
    public void startTournament(@PathVariable String tournamentId){
        commandGateway.send(new StartTournamentCommand(tournamentId));
    }

}
