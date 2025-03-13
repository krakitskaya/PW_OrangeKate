package com.kate.project.helpers;

import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static io.restassured.RestAssured.given;

public class CookieHelper {
    private static final String baseUri = Config.get("BASE_URL");

    public static String getCookieAfterLogin() {

        Response responseForLoginCall = given().baseUri(baseUri).log().all().get("auth/login");
        responseForLoginCall.then().log().all();
        String html = responseForLoginCall.getBody().asString();

        // Parse HTML with Jsoup
        Document doc = Jsoup.parse(html);

        // Extract token from `auth-login` tag
        Element authLogin = doc.selectFirst("auth-login");
        String token = authLogin != null ? authLogin.attr(":token").replaceAll("[\"']", "").replaceAll("^\"|\"$", "") : "Token not found";
        System.out.println("Extracted Auth Token: " + token);


        String sessionCookie = responseForLoginCall.getCookie("orangehrm");
        System.out.println("Extracted Cookie: " + sessionCookie);

        Response responseForAuthCall = given().baseUri(baseUri)
                .contentType("application/x-www-form-urlencoded")
                .formParam("_token", token)
                .formParam("username", Config.get("adminUsername"))
                .formParam("password", Config.get("adminPassword"))
                .cookie("orangehrm", sessionCookie)
                .log().all()
                .post("auth/validate");
        responseForAuthCall.then().log().all();

        String sessionCookieAfterLogin = responseForAuthCall.getCookie("orangehrm");

        System.out.println("Extracted Cookie: " + sessionCookieAfterLogin);
        return sessionCookieAfterLogin;
    }
}