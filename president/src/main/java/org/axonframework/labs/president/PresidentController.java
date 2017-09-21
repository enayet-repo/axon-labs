package org.axonframework.labs.president;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PresidentController {

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

}
