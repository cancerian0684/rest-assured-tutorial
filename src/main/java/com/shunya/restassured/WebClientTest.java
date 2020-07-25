package com.shunya.restassured;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class WebClientTest implements CommandLineRunner {

    private final WebClient webClient;

    public WebClientTest(WebClient.Builder builder) {
        webClient = builder
                .baseUrl("http://localhost:8080")
                .filter(ExchangeFilterFunctions.basicAuthentication("admin", "password"))
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth("admin", "password"))
                .build();
    }

    public void test() {
        final String flux = webClient.get()
                .uri("/secured/hello")
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "password").getBytes(UTF_8)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(flux);
    }

    public void test2() {
        final String flux = webClient.get()
                .uri("/secured/hello")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("admin", "password"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(flux);
    }

    public void test3() {
        final String flux = webClient.get()
                .uri("/secured/hello")
                .headers(httpHeaders -> httpHeaders.setBearerAuth("<bearer-token>"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(flux);
    }

    @Override
    public void run(String... args) throws Exception {
        test();
    }
}
