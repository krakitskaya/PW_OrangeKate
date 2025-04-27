package com.kate.project.api.interfaces;

import com.kate.project.api.ErrorResponse;
import io.restassured.response.Response;

public interface HandledResponse<R> {
    Response originalResponse();

    R body();

    ErrorResponse errorResponse();

    default int statusCode() {
        return originalResponse().getStatusCode();
    }

    default boolean isSuccessful() {
        int code = statusCode();
        return code >= 200 && code < 300;
    }
}