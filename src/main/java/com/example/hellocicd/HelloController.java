package com.example.hellocicd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Hello! CI/CD Pipeline is working perfectly!";
    }

    @GetMapping("/hello/{name}")
    public String greet(@PathVariable String name) {
        return "Hello " + name + "! Jenkins built and tested this automatically!";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is UP and running!";
    }
}