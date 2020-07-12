package com.shunya.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class MockMvcTest {

    @Test
    public void testMockMvc() {
        given().
                standaloneSetup(new DemoController()).
                param("name", "Johan").
        when().
                get("/greeting").
        then().
                statusCode(200).
                body("id", equalTo(1)).
                body("content", equalTo("Hello, Johan!"));
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
}
