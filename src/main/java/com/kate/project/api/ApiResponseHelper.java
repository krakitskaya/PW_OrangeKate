package com.kate.project.api;

import io.restassured.response.Response;

public class ApiResponseHelper {

    public static <R> ApiResponse<R> wrap(Response response, R body) {
        ErrorResponse error = response.statusCode() >= 400 ? parseError(response) : null;
        return new ApiResponse<>(response, body, error);
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