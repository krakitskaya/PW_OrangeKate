package com.kate.project.api.clients;

import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.ApiResponse;
import com.kate.project.api.ApiResponseHelper;
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
import java.util.function.Function;

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
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }

    protected <R> ApiResponse<R> sendAndWrap(Method method, String endpoint, RequestSpecification spec, R body) {
        Response response = send(method, endpoint, spec);
        return ApiResponseHelper.wrap(response, body);
    }

    protected ApiResponse<Void> sendAndWrap(Method method, String endpoint, RequestSpecification spec) {
        return sendAndWrap(method, endpoint, spec, null);
    }

    protected <T> T parseSuccessBody(Response response, Function<Response, T> parser) {
        if (response.statusCode() == 200) {
            return parser.apply(response);
        }
        return null;
    }

    private static String getCookieAfterLogin() {
        return getCookieAfterLogin(ADMIN_USER);
    }

    private static String getCookieAfterLogin(User user) {
        if (user == null) return getCookieAfterLogin();

        return sessionCookieCache.computeIfAbsent(user.getUsername(), username -> {
            Response loginPageResponse = given()
                    .baseUri(REST_BASE_URL)
                    .log().all()
                    .get("auth/login");

            String html = loginPageResponse.getBody().asString();
            Document doc = Jsoup.parse(html);
            Element authLoginElement = doc.selectFirst("auth-login");

            String token = extractToken(authLoginElement);
            String initialSessionCookie = loginPageResponse.getCookie("orangehrm");

            Response validateResponse = given()
                    .baseUri(REST_BASE_URL)
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("_token", token)
                    .formParam("username", user.getUsername())
                    .formParam("password", user.getPassword())
                    .cookie("orangehrm", initialSessionCookie)
                    .log().all()
                    .post("auth/validate");

            return validateResponse.getCookie("orangehrm");
        });
    }

    private static String extractToken(Element authLoginElement) {
        if (authLoginElement == null) {
            return "Token not found";
        }
        String rawToken = authLoginElement.attr(":token").trim();
        if (rawToken.startsWith("\"") && rawToken.endsWith("\"") && rawToken.length() > 1) {
            return rawToken.substring(1, rawToken.length() - 1);
        }
        return rawToken;
    }
}
