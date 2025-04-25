package com.kate.project.api.clients;

import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.common.Config;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.kate.project.common.Users.ADMIN_USER;
import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {
    protected static final String REST_BASE_URL = Config.get("BASE_URL");
    private static final Map<String, String> sessionCookieCache = new ConcurrentHashMap<>();

    protected RequestSpecification newRequest(User user) {
        String cookie = getCookieAfterLogin(user);
        return ApiRequestBuilder.start(REST_BASE_URL + "/api/v2", cookie);
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
        return getCookieAfterLogin(ADMIN_USER);
    }

    private static String getCookieAfterLogin(User user) {
        if (user == null) return getCookieAfterLogin();

        return sessionCookieCache.computeIfAbsent(user.getUsername(), username -> {
            Response responseForLoginCall = given().baseUri(REST_BASE_URL).log().all().get("auth/login");
            String html = responseForLoginCall.getBody().asString();

            Document doc = Jsoup.parse(html);
            Element authLogin = doc.selectFirst("auth-login");
            String token = authLogin != null ? authLogin.attr(":token").replaceAll("[\"']", "").replaceAll("^\"|\"$", "") : "Token not found";

            String sessionCookie = responseForLoginCall.getCookie("orangehrm");

            Response responseForAuthCall = given().baseUri(REST_BASE_URL)
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("_token", token)
                    .formParam("username", user.getUsername())
                    .formParam("password", user.getPassword())
                    .cookie("orangehrm", sessionCookie)
                    .log().all()
                    .post("auth/validate");

            return responseForAuthCall.getCookie("orangehrm");
        });
    }
}