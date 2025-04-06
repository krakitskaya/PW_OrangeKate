package com.kate.project.api.clients.buzz;

import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class CreatePostClient implements ResponseVerifier {
    private final ApiContext context;
    private final String text;
    private final String CREATE_POST_URL = "/buzz/posts";
    private final Method method = Method.POST;

    public CreatePostClient(ApiContext context, String text) {
        this.context = context;
        this.text = text;
    }

    public ApiRequestBuilder prepareBuilder() {
        return context.newRequest(method, CREATE_POST_URL)
                .withBody(new BuzzPostDto(text));
    }

    public Response send() {
        return context.newRequest()
                .withBody(new BuzzPostDto(text))
                .send(method, CREATE_POST_URL);
    }

    public int sendAndExtractPostId() {
        Response response = send();
        verifySuccess(response);
        return response.jsonPath().getInt("data.post.id");
    }
}