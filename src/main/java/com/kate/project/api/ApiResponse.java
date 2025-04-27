package com.kate.project.api;

import com.kate.project.api.interfaces.HandledResponse;
import io.restassured.response.Response;

public class ApiResponse<R> implements HandledResponse<R> {
    private final Response response;
    private final R body;
    private final ErrorResponse errorResponse;

    public ApiResponse(Response response, R body, ErrorResponse errorResponse) {
        this.response = response;
        this.body = body;
        this.errorResponse = errorResponse;
    }

    @Override
    public Response originalResponse() {
        return response;
    }

    @Override
    public R body() {
        return body;
    }

    @Override
    public ErrorResponse errorResponse() {
        return errorResponse;
    }

    public void assertSuccess() {
        if (!isSuccessful()) {
            throw new AssertionError("Expected success, but got status: " + statusCode());
        }
    }
}