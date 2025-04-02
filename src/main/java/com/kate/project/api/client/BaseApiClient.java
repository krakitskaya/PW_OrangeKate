package com.kate.project.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kate.project.helpers.Config;
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
    protected final String BASE_URI = REST_BASE_URL + "/api/v2";
    protected final String sessionCookie;
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected BaseApiClient(User user) {
        sessionCookie = generateSessionCookie(user);
    }

    protected RequestSpecification requestSpec() {
        return given()
                .baseUri(BASE_URI)
                .header("Content-Type", "application/json")
                .cookie("orangehrm", sessionCookie)
                .log().all();
    }

    protected Response sendRequest(Method method, String endpoint, Object body) {
        try {
            RequestSpecification request = requestSpec();

            if (body != null) {
                String jsonBody = objectMapper.writeValueAsString(body);
                request.body(jsonBody);
            }

            Response response = switch (method) {
                case POST -> request.post(endpoint);
                case PUT -> request.put(endpoint);
                case DELETE -> request.delete(endpoint);
                case GET -> request.get(endpoint);
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };

            response.then()
                    .log().body()
                    .statusCode(200);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }

    private String generateSessionCookie(User user) {
        if (user == null) {
            return getCookieAfterLogin(); // default admin
        }
        return getCookieAfterLogin(user);
    }

    public static String getCookieAfterLogin() {
        User admin = new User(Config.get("adminUsername"), Config.get("adminPassword"));
        return getCookieAfterLogin(admin);
    }

    public static String getCookieAfterLogin(User user) {

        Response responseForLoginCall = given().baseUri(REST_BASE_URL).log().all().get("auth/login");
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

        Response responseForAuthCall = given().baseUri(REST_BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .formParam("_token", token)
                .formParam("username", user.getUsername())
                .formParam("password", user.getPassword())
                .cookie("orangehrm", sessionCookie)
                .log().all()
                .post("auth/validate");
        responseForAuthCall.then().log().all();

        String sessionCookieAfterLogin = responseForAuthCall.getCookie("orangehrm");

        System.out.println("Extracted Cookie: " + sessionCookieAfterLogin);
        return sessionCookieAfterLogin;
    }
}