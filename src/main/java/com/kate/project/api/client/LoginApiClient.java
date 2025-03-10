package com.kate.project.api.client;

import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static io.restassured.RestAssured.given;

public class LoginApiClient {
    private final String baseUri;

    public LoginApiClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getTokenFromLoginPage() {

        Response response = given().baseUri(baseUri).log().all().get("auth/login");
        response.then().log().all();
        String html = response.getBody().asString();

        // Parse HTML with Jsoup
        Document doc = Jsoup.parse(html);

        // Extract token from `auth-login` tag
        Element authLogin = doc.selectFirst("auth-login");
        String token = authLogin != null ? authLogin.attr(":token").replaceAll("[\"']", "") : "Token not found";
        System.out.println("Extracted Auth Token: " + token);

        return token;
    }
}
