package com.kate.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Method;
import lombok.Getter;

@Getter
public class ApiContext {
    private final String baseUri;
    private final String sessionCookie;
    private final ObjectMapper objectMapper;

    public ApiContext(String baseUri, String sessionCookie, ObjectMapper objectMapper) {
        this.baseUri = baseUri;
        this.sessionCookie = sessionCookie;
        this.objectMapper = objectMapper;
    }

    public ApiRequestBuilder newRequest() {
        return new ApiRequestBuilder(baseUri, sessionCookie, objectMapper);
    }

    public ApiRequestBuilder newRequest(Method method, String endpoint) {
        return new ApiRequestBuilder(baseUri, sessionCookie, objectMapper, method, endpoint);
    }

}