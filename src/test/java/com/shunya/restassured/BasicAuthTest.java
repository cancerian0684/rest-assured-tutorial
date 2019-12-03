package com.shunya.restassured;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class BasicAuthTest {

    private final Logger logger = LoggerFactory.getLogger(BasicAuthTest.class);

    @Test
    public void basicAuthLogin() {
        String username = "admin";
        String password = "password";

        //language=JSON
        String jsonBody = "{\n" +
                "  \"name\": \"Foo\"\n" +
                "}";

        given().log().uri()
                .log().method()
                .log().headers()
                .log().body()
                .auth().preemptive().basic(username, password)
                .body(jsonBody)
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:8080/secured/hello")
                .then()
                .log().body()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", equalTo("hello Foo"));
    }
}
