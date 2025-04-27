package com.kate.project.api.interfaces;

import com.kate.project.api.ApiResponse;
import com.kate.project.api.ErrorResponse;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public interface ResponseVerifier {

    default void verifyStatus(Response response, int statusCode) {
        response.then().log().body().statusCode(statusCode);
    }

    default void verifyUnauthorized(Response response) {
        verifyStatus(response, 401);
    }

    default void verifyForbidden(Response response) {
        verifyStatus(response, 403);
    }

    default void verifyBadRequest(Response response) {
        verifyStatus(response, 400);
    }

    default void verifyNotFound(Response response) {
        verifyStatus(response, 404);
    }

    default void verifyConflict(Response response) {
        verifyStatus(response, 409);
    }

    default void verifyError(ApiResponse<?> apiResponse, int expectedStatus, String expectedMessage) {
        verifyStatus(apiResponse.originalResponse(), expectedStatus);

        ErrorResponse error = apiResponse.errorResponse();
        assertNotNull(error, "ErrorResponse should not be null");
        assertEquals(expectedMessage, error.message(), "Error message mismatch");
    }
}