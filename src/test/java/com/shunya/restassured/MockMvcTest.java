package com.shunya.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;

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
}
