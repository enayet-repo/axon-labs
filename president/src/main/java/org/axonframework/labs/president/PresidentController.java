package org.axonframework.labs.president;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PresidentController {

    private final CommandGateway commandGateway;

    @Autowired
    public PresidentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/full-match")
    public void buildFullMatch() {
        String matchId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMatchCommand(matchId));
        commandGateway.send(new JoinMatchCommand(matchId));
        String gameId = UUID.randomUUID().toString();
        commandGateway.sendAndWait(new StartMatchCommand(matchId, gameId));
        commandGateway.send(new PlayCardsCommand(matchId, gameId));
        commandGateway.send(new PassCommand(matchId, gameId));
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

    @GetMapping("/start-match/{matchId}/{gameId}")
    public void startMatch(@PathVariable String matchId, @PathVariable String gameId) {
        commandGateway.send(new StartMatchCommand(matchId, gameId));
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
