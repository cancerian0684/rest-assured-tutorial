package com.shunya.restassured

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

class MockMvcKotlinTest {

    @Test
    fun testMockMvc() {
        RestAssuredMockMvc.given()
                .standaloneSetup(DemoController()).param("name", "Johan")
                .`when`()["/greeting"]
                .then()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(1))
                .body("content", CoreMatchers.equalTo("Hello, Johan!"))
    }
}
