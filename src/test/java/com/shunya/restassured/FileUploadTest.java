package com.shunya.restassured;

import io.restassured.http.ContentType;
import org.junit.Test;
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
                .multiPart(new File("/Users/MunishChandel/Desktop/Screenshot_1562584935.png"))
                .accept(ContentType.JSON)
                .expect()
                .body("status", equalTo(false))
                .when()
                .post("http://localhost:8080/convert");
    }
}
