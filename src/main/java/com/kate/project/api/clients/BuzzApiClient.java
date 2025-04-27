package com.kate.project.api.clients;

import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.ApiResponse;
import com.kate.project.api.ApiResponseHelper;
import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

public class BuzzApiClient extends BaseApiClient {
    public static final String BASE_BUZZ_URL = "/buzz";
    private static final String CREATE_POST_URL = BASE_BUZZ_URL + "/posts";
    private static final String DELETE_POST_URL = BASE_BUZZ_URL + "/shares/";
    private static final String GET_POSTS_URL = BASE_BUZZ_URL + "/feed";

    public ApiResponse<Integer> createPost(BuzzPostDto dto, User user) {
        var spec = ApiRequestBuilder.withBody(newRequest(user), dto);
        var response = send(Method.POST, CREATE_POST_URL, spec);
        Integer postId = parseSuccessBody(response, r -> r.jsonPath().getInt("data.post.id"));
        return ApiResponseHelper.wrap(response, postId);
    }

    public ApiResponse<Void> deletePost(int postId, User user) {
        return sendAndWrap(Method.DELETE, DELETE_POST_URL + postId, newRequest(user));
    }

    public ApiResponse<List<BuzzPostDto>> getPosts(User user) {
        return getPosts(10, 0, "DESC", "share.createdAtUtc", user);
    }

    public ApiResponse<List<BuzzPostDto>> getPosts(Integer limit, Integer offset, String sortOrder, String sortField, User user) {
        RequestSpecification spec = newRequest(user);

        if (limit != null) spec = ApiRequestBuilder.withQueryParam(spec, "limit", limit);
        if (offset != null) spec = ApiRequestBuilder.withQueryParam(spec, "offset", offset);
        if (sortOrder != null) spec = ApiRequestBuilder.withQueryParam(spec, "sortOrder", sortOrder);
        if (sortField != null) spec = ApiRequestBuilder.withQueryParam(spec, "sortField", sortField);

        var response = send(Method.GET, GET_POSTS_URL, spec);

        List<Map<String, Object>> data = response.jsonPath().getList("data");
        List<BuzzPostDto> posts = data.stream()
                .map(item -> new BuzzPostDto(
                        (String) item.get("text"),
                        (String) item.getOrDefault("type", "text")
                ))
                .toList();

        return ApiResponseHelper.wrap(response, posts);
    }
}