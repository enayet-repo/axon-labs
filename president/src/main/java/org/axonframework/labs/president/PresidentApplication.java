package org.axonframework.labs.president;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
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
    public SseEmitter sseEmitter() {
        return new SseEmitter();
    }

    @Bean
    public EventScheduler eventScheduler(EventBus eventBus) {
        return new SimpleEventScheduler(Executors.newScheduledThreadPool(1), eventBus);
    }

}
