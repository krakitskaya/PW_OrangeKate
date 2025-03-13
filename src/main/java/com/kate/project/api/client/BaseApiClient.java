package com.kate.project.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kate.project.helpers.Config;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {
    protected final String BASE_URI = Config.get("BASE_URL") + "/api/v2";
    protected final String sessionCookie;
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected BaseApiClient(String sessionCookie) {
        this.sessionCookie = sessionCookie;
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
}