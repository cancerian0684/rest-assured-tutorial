package com.shunya.restassured;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class MockMvcTest {

    @Test
    public void testMockMvc() {
        given().
                standaloneSetup(new DemoController()).
                param("name", "John").
                when().
                get("/greeting").
                then().
                statusCode(200).
                body("id", equalTo(1)).
                body("content", equalTo("Hello, John!"));
    }

    @Test
    public void testMockMvcPayload() {
        given().
                standaloneSetup(new DemoController()).
                when().
                get("/lotto").
                then().
                statusCode(200).
                body("lotto.lottoId", equalTo(5),
                        "lotto.winners.winnerId", hasItems(23, 54));
    }

    @Test
    public void testMockMvcGetPlainText() {
        RestAssured.registerParser("text/plain", Parser.TEXT);
        given().standaloneSetup(new PingController())
                .when().get("/ping")
                .then().assertThat()
                    .statusCode(200)
                    .body(equalTo("pong"));
    }

    @Test
    public void testMockMvcGetPingJson() {
        given().standaloneSetup(new PingController())
                .when().get("/ping-json")
                .then().assertThat()
                    .statusCode(200)
                    .body("ping", equalTo("pong"));
    }
}
