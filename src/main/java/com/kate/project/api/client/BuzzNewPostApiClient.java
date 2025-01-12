package com.kate.project.api.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BuzzNewPostApiClient {
    private final String baseUri;
    private final String sessionCookie;
    private final Runnable cleanupCallback; // New field for post-call cleanup

    public BuzzNewPostApiClient(String baseUri, String sessionCookie, Runnable cleanupCallback) {
        this.baseUri = baseUri;
        this.sessionCookie = sessionCookie;
        this.cleanupCallback = cleanupCallback;
    }

    public Response createNewPost(String text, String type) {
        String requestBody = """
                {
                    "text": "%s",
                    "type": "%s"
                }
                """.formatted(text, type);

        Response response = given()
                .baseUri(baseUri)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Origin", "https://opensource-demo.orangehrmlive.com")
                .header("Referer", "https://opensource-demo.orangehrmlive.com/web/buzz/viewBuzz")
                .cookie("orangehrm", sessionCookie)
                .body(requestBody)
                .log().all()
                .post("/buzz/posts");

        if (cleanupCallback != null) {
            cleanupCallback.run(); // Invoke the cleanup callback
        }

        return response;
    }
}