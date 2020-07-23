package com.shunya.restassured;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String pong() {
        return "pong";
    }

    @GetMapping(value = "/ping-json", produces = "application/json")
    public String pingJson() {
        return "{\n" +
                "  \"ping\": \"pong\"\n" +
                "}";
    }
}
