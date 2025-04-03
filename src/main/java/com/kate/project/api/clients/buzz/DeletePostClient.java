package com.kate.project.api.clients.buzz;

import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class DeletePostClient {
    private final ApiContext context;
    private final int postId;
    private final String endpoint;
    private final Method method = Method.DELETE;

    public DeletePostClient(ApiContext context, int postId) {
        this.context = context;
        this.postId = postId;
        this.endpoint = "/buzz/shares/" + postId;
    }

    public ApiRequestBuilder prepareBuilder() {
        return context.newRequest(method, endpoint);
    }

    public Response send() {
        return context.newRequest().send(method, endpoint);
    }

    public void sendAndVerifySuccess() {
        send().then().statusCode(200).log().body();
    }
}