package com.kate.project.api.clients.buzz;

import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.interfaces.ResponseVerifier;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class DeletePostClient implements ResponseVerifier {
    private final ApiContext context;
    private final String DELETE_POST_URL;
    private final Method method = Method.DELETE;

    public DeletePostClient(ApiContext context, int postId) {
        this.context = context;
        this.DELETE_POST_URL = "/buzz/shares/" + postId;
    }

    public ApiRequestBuilder prepareBuilder() {
        return context.newRequest(method, DELETE_POST_URL);
    }

    public Response send() {
        return context.newRequest().send(method, DELETE_POST_URL);
    }

    public void sendAndVerifySuccess() {
        verifySuccess(send());
    }
}