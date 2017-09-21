package org.axonframework.labs.president;

import org.axonframework.config.EventHandlingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SpringBootApplication
public class PresidentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresidentApplication.class, args);
    }

    @Autowired
    public void setProcessingGroup(EventHandlingConfiguration eventHandlingConfiguration) {
        eventHandlingConfiguration.usingTrackingProcessors();
    }

    @Bean
    public SseEmitter sseEmiter() {
        return new SseEmitter();
    }

}
