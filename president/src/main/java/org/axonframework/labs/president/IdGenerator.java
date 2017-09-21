package org.axonframework.labs.president;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class IdGenerator {

    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
