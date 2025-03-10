package com.kate.project.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {
    protected final String baseUri;
    protected final String sessionCookie;
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected BaseApiClient(String baseUri, String sessionCookie) {
        this.baseUri = baseUri;
        this.sessionCookie = sessionCookie;
    }

    protected RequestSpecification requestSpec() {
        return given()
                .baseUri(baseUri)
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
                    .statusCode(200)
                    .log().all();

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }
}