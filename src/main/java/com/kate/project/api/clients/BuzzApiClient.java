package com.kate.project.api.clients;

import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class BuzzApiClient extends BaseApiClient implements ResponseVerifier {
    public static final String BASE_BUZZ_URL = "/buzz";
    public static final String CREATE_POST_URL = BASE_BUZZ_URL + "/posts";
    public static final String DELETE_POST_URL = BASE_BUZZ_URL + "/shares/";
    public static final String GET_POSTS_URL = BASE_BUZZ_URL + "/feed";

    public BuzzApiClient(User user) {
        super(user);
    }

    public Response createPost(BuzzPostDto buzzPostDto) {
        return context.newRequest()
                .withBody(buzzPostDto)
                .send(Method.POST, CREATE_POST_URL);
    }

    public int createPostAndVerifySuccess(BuzzPostDto buzzPostDto) {
        Response response = createPost(buzzPostDto);
        verifySuccess(response);
        return response.jsonPath().getInt("data.post.id");
    }

    public Response deletePost(int postId) {
        return context.newRequest().send(Method.DELETE, DELETE_POST_URL + postId);
    }

    public void deletePostAndVerifySuccess(int postId) {
        verifySuccess(deletePost(postId));
    }

    public List<BuzzPostDto> getPosts() {
        return getPosts(10, 0, "DESC", "share.createdAtUtc");
    }

    public List<BuzzPostDto> getPosts(Integer limit, Integer offset, String sortOrder, String sortField) {
        ApiRequestBuilder builder = context.newRequest(Method.GET, GET_POSTS_URL);
        if (limit != null) builder.withQueryParam("limit", limit);
        if (offset != null) builder.withQueryParam("offset", offset);
        if (sortOrder != null) builder.withQueryParam("sortOrder", sortOrder);
        if (sortField != null) builder.withQueryParam("sortField", sortField);
        Response response = builder.send();
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