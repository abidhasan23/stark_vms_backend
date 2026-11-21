package com.example.stark_vms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of("service", "stark-vms-backend", "status", "UP", "api", "/api/vehicles");
    }
}
