package org.axonframework.labs.president;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class PresidentController {

    private final CommandGateway commandGateway;
    private final SseEmitter sseEmitter;

    @Autowired
    public PresidentController(CommandGateway commandGateway,
                               SseEmitter sseEmitter) {
        this.commandGateway = commandGateway;
        this.sseEmitter = sseEmitter;
    }

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/full-match")
    public SseEmitter buildFullMatch() {
        String matchId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMatchCommand(matchId));
        commandGateway.send(new JoinMatchCommand(matchId));
        String gameId = commandGateway.sendAndWait(new StartMatchCommand(matchId));
        commandGateway.send(new PlayCardsCommand(matchId, gameId));
        commandGateway.send(new PassCommand(matchId, gameId));
        return sseEmitter;
    }

    @GetMapping("/create-match")
    public String createMatch() {
        String aggregateIdentifier = UUID.randomUUID().toString();
        commandGateway.send(new CreateMatchCommand(aggregateIdentifier));
        return aggregateIdentifier;
    }

    @GetMapping("/join-match/{matchId}")
    public void joinMatch(@PathVariable String matchId) {
        commandGateway.send(new JoinMatchCommand(matchId));
    }

    @GetMapping("/start-match/{matchId}")
    public void startMatch(@PathVariable String matchId) {
        commandGateway.send(new StartMatchCommand(matchId));
    }

    @GetMapping("/play-cards/{matchId}/{gameId}")
    public void playCards(@PathVariable String matchId, @PathVariable String gameId) {
        commandGateway.send(new PlayCardsCommand(matchId, gameId));
    }

    @GetMapping("/pass/{matchId}/{gameId}")
    public void pass(@PathVariable String matchId, @PathVariable String gameId) {
        commandGateway.send(new PassCommand(matchId, gameId));
    }

}
