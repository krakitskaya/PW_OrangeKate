package com.kate.project.api.clients;

import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

    public Response createPost(BuzzPostDto dto) {
        RequestSpecification spec = ApiRequestBuilder.withBody(newRequest(), dto);
        return send(Method.POST, CREATE_POST_URL, spec);
    }

    public int createPostAndVerifySuccess(BuzzPostDto dto) {
        Response response = createPost(dto);
        verifySuccess(response);
        return response.jsonPath().getInt("data.post.id");
    }

    public Response deletePost(int postId) {
        return send(Method.DELETE, DELETE_POST_URL + postId, newRequest());
    }

    public void deletePostAndVerifySuccess(int postId) {
        verifySuccess(deletePost(postId));
    }

    public List<BuzzPostDto> getPosts(Integer limit, Integer offset, String sortOrder, String sortField) {
        RequestSpecification spec = newRequest();

        if (limit != null) spec = ApiRequestBuilder.withQueryParam(spec, "limit", limit);
        if (offset != null) spec = ApiRequestBuilder.withQueryParam(spec, "offset", offset);
        if (sortOrder != null) spec = ApiRequestBuilder.withQueryParam(spec, "sortOrder", sortOrder);
        if (sortField != null) spec = ApiRequestBuilder.withQueryParam(spec, "sortField", sortField);

        Response response = send(Method.GET, GET_POSTS_URL, spec);
        verifySuccess(response);

        List<Map<String, Object>> rawData = response.jsonPath().getList("data");

        return rawData.stream()
                .map(item -> new BuzzPostDto(
                        (String) item.get("text"),
                        (String) item.getOrDefault("type", "text")
                ))
                .toList();
    }

    public List<BuzzPostDto> getPosts() {
        return getPosts(10, 0, "DESC", "share.createdAtUtc");
    }
}