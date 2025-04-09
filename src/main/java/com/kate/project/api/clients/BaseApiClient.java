package com.kate.project.api.clients;

import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.Config;
import com.kate.project.api.enums.UserRole;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {
    protected static final String REST_BASE_URL = Config.get("BASE_URL");
    protected final String sessionCookie;

    protected BaseApiClient(User user) {
        this.sessionCookie = getCookieAfterLogin(user);
    }

    protected RequestSpecification newRequest() {
        return ApiRequestBuilder.start(REST_BASE_URL + "/api/v2", sessionCookie);
    }

    protected Response send(Method method, String endpoint, RequestSpecification spec) {
        return switch (method) {
            case POST -> spec.post(endpoint);
            case PUT -> spec.put(endpoint);
            case DELETE -> spec.delete(endpoint);
            case GET -> spec.get(endpoint);
            default -> throw new IllegalArgumentException("Unsupported HTTP method");
        };
    }

    private static String getCookieAfterLogin() {
        User admin = new User(Config.get("adminUsername"), Config.get("adminPassword"), UserRole.ADMIN);
        return getCookieAfterLogin(admin);
    }

    private static String getCookieAfterLogin(User user) {
        if (user == null) return getCookieAfterLogin();

        Response responseForLoginCall = given().baseUri(REST_BASE_URL).log().all().get("auth/login");
        String html = responseForLoginCall.getBody().asString();

        Document doc = Jsoup.parse(html);
        Element authLogin = doc.selectFirst("auth-login");
        String token = authLogin != null ? authLogin.attr(":token").replaceAll("[\"']", "").replaceAll("^\"|\"$", "") : "Token not found";
        System.out.println("Extracted Auth Token: " + token);

        String sessionCookie = responseForLoginCall.getCookie("orangehrm");
        System.out.println("Extracted Cookie: " + sessionCookie);

        Response responseForAuthCall = given().baseUri(REST_BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .formParam("_token", token)
                .formParam("username", user.getUsername())
                .formParam("password", user.getPassword())
                .cookie("orangehrm", sessionCookie)
                .log().all()
                .post("auth/validate");

        String sessionCookieAfterLogin = responseForAuthCall.getCookie("orangehrm");

        System.out.println("Extracted Cookie: " + sessionCookieAfterLogin);
        return sessionCookieAfterLogin;
    }
}