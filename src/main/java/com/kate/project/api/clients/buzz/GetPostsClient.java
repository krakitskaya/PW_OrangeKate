package com.kate.project.api.clients.buzz;

import com.kate.project.api.ApiContext;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class GetPostsClient implements ResponseVerifier {
    private final ApiContext context;
    private final String GET_POSTS_URL = "/buzz/feed";
    private final Method method = Method.GET;

    private Integer limit = 10;
    private Integer offset = 0;
    private String sortOrder = "DESC";
    private String sortField = "share.createdAtUtc";

    public GetPostsClient(ApiContext context) {
        this.context = context;
    }

    public GetPostsClient withLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public GetPostsClient withOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public GetPostsClient withSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public GetPostsClient withSortField(String sortField) {
        this.sortField = sortField;
        return this;
    }

    public ApiRequestBuilder prepareBuilder() {
        ApiRequestBuilder builder = context.newRequest(method, GET_POSTS_URL);
        if (limit != null) builder.withQueryParam("limit", limit);
        if (offset != null) builder.withQueryParam("offset", offset);
        if (sortOrder != null) builder.withQueryParam("sortOrder", sortOrder);
        if (sortField != null) builder.withQueryParam("sortField", sortField);
        return builder;
    }

    public Response send() {
        return prepareBuilder().send();
    }

    public List<BuzzPostDto> getPosts() {
        Response response = send();
        verifySuccess(response);

        List<Map<String, Object>> rawData = response.jsonPath().getList("data");

        return rawData.stream()
                .map(item -> new BuzzPostDto(
                        (String) item.get("text"),
                        (String) item.getOrDefault("type", "text")
                ))
                .toList();
    }
}