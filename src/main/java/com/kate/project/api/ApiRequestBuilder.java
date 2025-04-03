package com.kate.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class ApiRequestBuilder {
    private final RequestSpecification spec;
    private final ObjectMapper objectMapper;
    private final String sessionCookie;

    private final Map<String, String> headers = new HashMap<>();
    private final Set<String> headersToRemove = new HashSet<>();
    private boolean skipCookie = false;
    private Object body;

    private Method method;
    private String endpoint;

    public ApiRequestBuilder(String baseUri, String sessionCookie, ObjectMapper objectMapper) {
        this.sessionCookie = sessionCookie;
        this.spec = given().baseUri(baseUri).log().all();
        this.objectMapper = objectMapper;

        headers.put("Content-Type", "application/json");
    }

    public ApiRequestBuilder(String baseUri, String sessionCookie, ObjectMapper objectMapper, Method method, String endpoint) {
        this(baseUri, sessionCookie, objectMapper);
        this.method = method;
        this.endpoint = endpoint;
    }

    public ApiRequestBuilder withHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ApiRequestBuilder removeHeader(String key) {
        headersToRemove.add(key);
        return this;
    }

    public ApiRequestBuilder withQueryParam(String key, Object value) {
        spec.queryParam(key, value);
        return this;
    }

    public ApiRequestBuilder withBody(Object body) {
        this.body = body;
        return this;
    }

    public ApiRequestBuilder removeCookie() {
        this.skipCookie = true;
        return this;
    }

    public Response send(Method method, String endpoint) {
        applyHeadersAndCookies();
        applyBody();

        return switch (method) {
            case POST -> spec.post(endpoint);
            case PUT -> spec.put(endpoint);
            case DELETE -> spec.delete(endpoint);
            case GET -> spec.get(endpoint);
            default -> throw new IllegalArgumentException("Unsupported HTTP method");
        };
    }

    public Response send() {
        if (method == null || endpoint == null) {
            throw new IllegalStateException("Method or endpoint not set.");
        }
        return send(method, endpoint);
    }

    private void applyHeadersAndCookies() {
        headers.forEach((key, value) -> {
            if (!headersToRemove.contains(key)) {
                spec.header(key, value);
            }
        });

        if (!skipCookie && sessionCookie != null && !sessionCookie.isEmpty()) {
            spec.cookie("orangehrm", sessionCookie);
        }
    }

    private void applyBody() {
        if (body != null) {
            try {
                spec.body(objectMapper.writeValueAsString(body));
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize request body", e);
            }
        }
    }
}