package com.shunya.restassured

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class BasicAuthKotlinTest {
    private val logger = LoggerFactory.getLogger(BasicAuthKotlinTest::class.java)

    @Test
    fun `basic auth login test using kotlin`() {
        val username = "admin"
        val password = "password"
        //language=JSON
        val jsonBody = "{" +
                "  \"name\": \"Foo\"\n" +
                "}"

        Given {
            log().uri()
            log().method()
            log().body()
            auth().preemptive().basic(username, password)
            body(jsonBody)
            contentType(ContentType.JSON)
        } When {
            post("http://localhost:8080/secured/hello")
        } Then {
            log().body()
            statusCode(200)
            body("success", IsEqual.equalTo(true))
            body("data", IsEqual.equalTo("hello Foo"))
        }
    }
}