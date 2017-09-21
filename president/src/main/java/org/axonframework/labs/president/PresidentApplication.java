package org.axonframework.labs.president;

import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PresidentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresidentApplication.class, args);
    }

    @Bean
    public Repository matchRepository(EntityManagerProvider entityManagerProvider, EventBus eventBus) {
        return new GenericJpaRepository<>(entityManagerProvider, Match.class, eventBus);
    }

}
