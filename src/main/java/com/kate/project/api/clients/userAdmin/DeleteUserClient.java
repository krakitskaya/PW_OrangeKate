package com.kate.project.api.clients.userAdmin;

import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class DeleteUserClient {
    private final ApiContext context;
    private final Integer userId;
    private final String endpoint = "/admin/users";
    private final Method method = Method.DELETE;

    public DeleteUserClient(ApiContext context, Integer userId) {
        this.context = context;
        this.userId = userId;
    }

    public ApiRequestBuilder prepareBuilder() {
        Map<String, List<Integer>> body = Map.of("ids", List.of(userId));
        return context.newRequest(method, endpoint).withBody(body);
    }

    public Response send() {
        Map<String, List<Integer>> body = Map.of("ids", List.of(userId));
        return context.newRequest().withBody(body).send(method, endpoint);
    }

    public void sendAndVerifySuccess() {
        send().then().statusCode(200).log().body();
    }
}
