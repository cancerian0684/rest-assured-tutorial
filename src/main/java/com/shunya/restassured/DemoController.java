package com.shunya.restassured;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class DemoController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = GET)
    @ResponseBody
    public Greeting greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "/lotto", method = GET, produces = "application/json")
    @ResponseBody
    public String getPayload() {
        String payload = "{\n" +
                "  \"lotto\":{\n" +
                "    \"lottoId\":5,\n" +
                "    \"winning-numbers\":[2,45,34,23,7,5,3],\n" +
                "    \"winners\":[\n" +
                "      {\n" +
                "        \"winnerId\":23,\n" +
                "        \"numbers\":[2,45,34,23,3,5]\n" +
                "      },\n" +
                "      {\n" +
                "        \"winnerId\":54,\n" +
                "        \"numbers\":[52,3,12,11,18,22]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        return payload;
    }
}
