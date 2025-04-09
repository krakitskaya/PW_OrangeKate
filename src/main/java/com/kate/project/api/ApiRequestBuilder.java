package com.kate.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiRequestBuilder {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static RequestSpecification start(String baseUri, String sessionCookie) {
        RequestSpecification spec = given().baseUri(baseUri).log().all();
        spec.header("Content-Type", "application/json");

        if (sessionCookie != null && !sessionCookie.isEmpty()) {
            spec.cookie("orangehrm", sessionCookie);
        }

        return spec;
    }

    public static RequestSpecification withQueryParam(RequestSpecification spec, String key, Object value) {
        return spec.queryParam(key, value);
    }

    public static RequestSpecification withBody(RequestSpecification spec, Object body) {
        try {
            return spec.body(objectMapper.writeValueAsString(body));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize request body", e);
        }
    }
}