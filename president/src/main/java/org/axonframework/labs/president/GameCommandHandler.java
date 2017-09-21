package org.axonframework.labs.president;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.stereotype.Service;

@Service
public class GameCommandHandler {

    private final Repository<Match> matchRepository;

    public GameCommandHandler(Repository<Match> matchRepository) {
        this.matchRepository = matchRepository;
    }

    @CommandHandler
    public void createMatch(CreateMatchCommand command) throws Exception {
        matchRepository.newInstance(() -> new Match(command));
    }

    @CommandHandler
    public void joinMatch(JoinMatchCommand command) {
        matchRepository.load(command.getAggregateIdentifier())
                       .execute(match -> match.handle(command));
    }

    @CommandHandler
    public void startMatch(StartMatchCommand command) {
        matchRepository.load(command.getAggregateIdentifier())
                       .execute(match -> match.handle(command));
    }

    @CommandHandler
    public void playCards(PlayCardsCommand command) {
        matchRepository.load(command.getAggregateIdentifier())
                       .execute(match -> match.handle(command));
    }

    @CommandHandler
    public void pass(PassCommand command) {
        matchRepository.load(command.getAggregateIdentifier())
                       .execute(match -> match.handle(command));
    }

}
