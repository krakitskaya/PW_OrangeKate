package com.kate.project.api.clients.buzz;

import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.PostNewBuzzRequestDto;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class CreatePostClient {
    private final ApiContext context;
    private final String text;
    private final String endpoint = "/buzz/posts";
    private final Method method = Method.POST;

    public CreatePostClient(ApiContext context, String text) {
        this.context = context;
        this.text = text;
    }

    public ApiRequestBuilder prepareBuilder() {
        return context.newRequest(method, endpoint)
                .withBody(new PostNewBuzzRequestDto(text));
    }

    public Response send() {
        return context.newRequest()
                .withBody(new PostNewBuzzRequestDto(text))
                .send(method, endpoint);
    }

    public int sendAndExtractPostId() {
        Response response = send();
        return response.jsonPath().getInt("data.post.id");
    }
}