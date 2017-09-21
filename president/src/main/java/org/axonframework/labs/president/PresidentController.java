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

    @GetMapping("/create-match")
    public String createMatch() {
        String aggregateIdentifier = UUID.randomUUID().toString();
        commandGateway.send(new CreateMatchCommand(aggregateIdentifier));
        return aggregateIdentifier;
    }

    @GetMapping("/join-match/{aggregateIdentifier}")
    public void joinMatch(@PathVariable String aggregateIdentifier) {
        commandGateway.send(new JoinMatchCommand(aggregateIdentifier));
    }

    @GetMapping("/start-match/{aggregateIdentifier}")
    public void startMatch(@PathVariable String aggregateIdentifier) {
        commandGateway.send(new StartMatchCommand(aggregateIdentifier));
    }

    @GetMapping("/play-cards/{aggregateIdentifier}")
    public void playCards(@PathVariable String aggregateIdentifier) {
        commandGateway.send(new PlayCardsCommand(aggregateIdentifier));
    }

    @GetMapping("/pass/{aggregateIdentifier}")
    public void pass(@PathVariable String aggregateIdentifier) {
        commandGateway.send(new PassCommand(aggregateIdentifier));
    }

}
