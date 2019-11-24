package com.shunya.restassured;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class OAuth2Test {

    private final Logger logger = LoggerFactory.getLogger(OAuth2Test.class);

    @Test
    public void testAuthWithResourceOwnerCredentials() throws JSONException {
        final String accessToken = resourceOwnerLogin("https://dapi.shunyafoundation.com/sso/oauth/token", "acme", "acmesecret", "8010106513", "test1234", "openid");
        logger.info("AccessToken = {}", accessToken);
    }

    @Test
    public void testAuthWithClientCredentials() throws JSONException {
        final String accessToken = clientCredentialsLogin("https://dapi.shunyafoundation.com/sso/oauth/token", "acme", "acmesecret", "openid");
        logger.info("AccessToken = {}", accessToken);
    }

    private String resourceOwnerLogin(String tokenUri, String clientId, String clientSecret, String username, String password, String scope) throws JSONException {
        logger.info("Getting OAuth Token from server - {}", tokenUri);
        Response response =
                given().auth().preemptive().basic(clientId, clientSecret)
                        .formParam("grant_type", "password")
                        .formParam("username", username)
                        .formParam("password", password)
                        .formParam("scope", scope)
                        .when()
                        .post(tokenUri);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();
        String tokenType = jsonObject.get("token_type").toString();
        logger.info("Oauth Token for {} with type {} is {}", username, tokenType, accessToken);
        return accessToken;
    }

    private String clientCredentialsLogin(String tokenUri, String clientId, String clientSecret, String scope) throws JSONException {
        logger.info("Getting OAuth Token from server - {}", tokenUri);
        Response response =
                given().auth().preemptive().basic(clientId, clientSecret)
                        .formParam("grant_type", "client_credentials")
                        .formParam("scope", scope)
                        .when()
                        .post(tokenUri);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();
        String tokenType = jsonObject.get("token_type").toString();
        logger.info("Oauth Token with type {} is {}", tokenType, accessToken);
        return accessToken;
    }

    public void callSecuredEndpoint(String accessToken) {
        //language=JSON
        String jsonString = "{\"id\": \"100\"}";
        Response response = given().auth().preemptive().oauth2(accessToken)
                .contentType("application/json")
                .body(jsonString)
                .when()
                .post("/api/feed");
        String responseBody = response.getBody().asString();
        if (response.getStatusCode() >= 200 && response.getStatusCode() <= 299) {
            logger.info("Create Daily Feed Response = " + responseBody);
        } else {
            logger.error("Error creating daily feed = {}", responseBody);
        }
    }

    public void getDataFromResourceServer(String accessToken) {
        Response response = given().auth().preemptive().oauth2(accessToken)
                .contentType("application/json")
                .when()
                .get("/api/feed");
        String responseBody = response.getBody().asString();
        if (response.getStatusCode() >= 200 && response.getStatusCode() <= 299) {
            logger.info("Create Daily Feed Response = " + responseBody);
        } else {
            logger.error("Error creating daily feed = {}", responseBody);
        }
    }
}
