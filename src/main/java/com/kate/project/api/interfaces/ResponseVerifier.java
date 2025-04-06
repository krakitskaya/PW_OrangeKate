package com.kate.project.api.interfaces;

import io.restassured.response.Response;

public interface ResponseVerifier  {

    default void verifySuccess(Response response) {
        response.then().log().body().statusCode(200);
    }

    default void verifyUnauthorized(Response response) {
        response.then().log().body().statusCode(401);
    }

    default void verifyForbidden(Response response) {
        response.then().log().body().statusCode(403);
    }

    default void verifyBadRequest(Response response) {
        response.then().log().body().statusCode(400);
    }

    default void verifyNotFound(Response response) {
        response.then().log().body().statusCode(404);
    }

    default void verifyConflict(Response response) {
        response.then().log().body().statusCode(409);
    }

    default void verifyCustomStatus(Response response, int expectedStatus) {
        response.then().log().body().statusCode(expectedStatus);
    }
}