package com.shunya.restassured;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class FileUploadTest {

    private final Logger logger = LoggerFactory.getLogger(FileUploadTest.class);

    @Test
    public void fileUploadTest() {
        given()
            .param("timestamp", new Date().getTime())
            .multiPart("file", new File("/home/munish/workspace/rest-assured-tutorial/build.gradle"))
            .accept(ContentType.JSON)
        .when()
            .post("http://localhost:8080/fileupload")
        .then()
            .statusCode(200)
            .body("success", equalTo(true));
    }
}
