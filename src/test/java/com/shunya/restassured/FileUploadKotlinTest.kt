package com.shunya.restassured

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import java.io.File

class FileUploadKotlinTest {

    @Test
    fun `test rest assured file upload with kotlin`() {
        val success: Boolean =
                Given {
                    port(8080)
                    multiPart("file", File("/home/munish/workspace/rest-assured-tutorial/build.gradle"))
                    accept(ContentType.JSON)
                } When {
                    post("http://localhost:8080/fileupload")
                } Then {
                    statusCode(200)
                    body("success", equalTo(true))
                } Extract {
                    path("success")
                }
        println("operation status = $success")
    }
}