package com.kate.project.api.clients.userAdmin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.CreatedUserDto;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class CreateUserClient {
    private final ApiContext context;
    private final Object body;
    private final String endpoint = "/admin/users";
    private final Method method = Method.POST;

    public CreateUserClient(ApiContext context, Object body) {
        this.context = context;
        this.body = body;
    }

    public ApiRequestBuilder prepareBuilder() {
        return context.newRequest(method, endpoint).withBody(body);
    }

    public Response send() {
        return context.newRequest().withBody(body).send(method, endpoint);
    }

    public CreatedUserDto sendAndExtractUser() {
        Response response = send();
        JsonNode dataNode = context.getObjectMapper().valueToTree(response.jsonPath().get("data"));
        try {
            return context.getObjectMapper().treeToValue(dataNode, CreatedUserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map response", e);
        }
    }
}

