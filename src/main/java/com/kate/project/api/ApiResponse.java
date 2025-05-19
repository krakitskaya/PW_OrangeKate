package com.kate.project.api;

import com.kate.project.api.interfaces.HandledResponse;
import io.restassured.response.Response;

public class ApiResponse<R> implements HandledResponse<R> {
    private Response response;
    private R body;
    private ErrorResponse errorResponse;

    public ApiResponse(Response response, R bodyIfSuccess) {
        this.response = response;

        if (isSuccessful()) {
            this.body = bodyIfSuccess;
        } else {
            this.errorResponse = parseError(response);
        }
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

    @Override
    public int statusCode() {
        return response.statusCode();
    }

    @Override
    public boolean isSuccessful() {
        int code = response.statusCode();
        return code >= 200 && code < 300;
    }

    public void assertSuccess() {
        if (!isSuccessful()) {
            throw new AssertionError("Expected success, but got status: " + statusCode());
        }
    }

    private static ErrorResponse parseError(Response response) {
        try {
            var jsonPath = response.jsonPath();
            String message = jsonPath.getString("error.message");

            int code = 0;
            try {
                code = jsonPath.getInt("error.code");
            } catch (Exception e) {
                try {
                    code = Integer.parseInt(jsonPath.getString("error.status"));
                } catch (Exception ignored) {
                }
            }

            return new ErrorResponse(message, code);
        } catch (Exception e) {
            return new ErrorResponse("Unknown error", response.statusCode());
        }
    }
}