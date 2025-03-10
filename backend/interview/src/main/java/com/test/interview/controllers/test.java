package com.test.interview.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class test {

    @GetMapping("/hola")
    public String decirHola() {
        return "¡Hola, mundo desde Spring Boot!";
    }
}
