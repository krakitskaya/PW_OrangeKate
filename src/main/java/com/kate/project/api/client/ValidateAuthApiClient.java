package com.kate.project.api.client;

import com.kate.project.helpers.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ValidateAuthApiClient {

    private final String baseUri;
    private final String token;

    public ValidateAuthApiClient(String baseUri, String token) {
        this.baseUri = baseUri;
        this.token = token.replaceAll("^\"|\"$", "");
    }

    public String getSessionCookie() {

        String requestBody = """
                {
                    "_token": "%s",
                    "username": "%s",
                    "password": "%s"
                }
                """.formatted(token, Config.get("username"), Config.get("password"));

        Response response = given().baseUri(baseUri)
                .contentType("application/x-www-form-urlencoded")
                .body(requestBody)
                .log().all()
                .post("auth/validate");
        response.then().log().all();

        String sessionCookie = response.getCookie("orangehrm");

        System.out.println("Extracted Cookie: " + sessionCookie);
        return sessionCookie;
    }
}