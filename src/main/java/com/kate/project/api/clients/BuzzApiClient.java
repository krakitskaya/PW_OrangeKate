package com.kate.project.api.clients;

import com.kate.project.api.*;
import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.interfaces.HandledResponse;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class BuzzApiClient extends BaseApiClient {
    public static final String BASE_BUZZ_URL = "/buzz";
    private static final String CREATE_POST_URL = BASE_BUZZ_URL + "/posts";
    private static final String DELETE_POST_URL = BASE_BUZZ_URL + "/shares/";
    private static final String GET_POSTS_URL = BASE_BUZZ_URL + "/feed";

    public ApiResponse<Integer> createPost(BuzzPostDto dto, User user) {
        var spec = ApiRequestBuilder.withBody(newRequest(user), dto);
        var response = send(Method.POST, CREATE_POST_URL, spec);
        Integer postId = parseSuccessBody(response, r -> r.jsonPath().getInt("data.post.id"));
        return new ApiResponse<>(response, postId);
    }

    public ApiResponse<Void> deletePost(int postId, User user) {
        return sendAndWrap(Method.DELETE, DELETE_POST_URL + postId, newRequest(user));
    }

    public ApiResponse<List<BuzzPostDto>> getPosts(User user) {
        var defaultParams = QueryParams.builder()
                .limit(10)
                .offset(0)
                .sortOrder("DESC")
                .sortField("share.createdAtUtc")
                .build();

        return getPosts(defaultParams, user);
    }

    public ApiResponse<List<BuzzPostDto>> getPosts(QueryParams params, User user) {
        var handled = getHandledPosts(params, user);
        return new ApiResponse<>(handled.originalResponse(), handled.body());
    }

    public HandledResponse<List<BuzzPostDto>> getHandledPosts(QueryParams params, User user) {
        RequestSpecification spec = params.applyTo(newRequest(user));
        var response = send(Method.GET, GET_POSTS_URL, spec);

        return new ListApiResponse<>(response, map -> new BuzzPostDto(
                (String) map.get("text"),
                (String) map.getOrDefault("type", "text")
        ));
    }
}