package com.kate.project.api.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.dto.CreatedUserDto;
import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

public class UserAdminApiClient extends BaseApiClient implements ResponseVerifier {
    public static final String BASE_USER_ADMIN_URL = "/admin/users";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UserAdminApiClient() {
        super();
    }

    public Response createUser(UserRequestDto userRequestDto, User user) {
        RequestSpecification spec = ApiRequestBuilder.withBody(newRequest(user), userRequestDto);
        return send(Method.POST, BASE_USER_ADMIN_URL, spec);
    }

    public CreatedUserDto createUserAndVerifySuccess(UserRequestDto userRequestDto, User user) {
        Response response = createUser(userRequestDto, user);
        verifySuccess(response);
        JsonNode dataNode = objectMapper.valueToTree(response.jsonPath().get("data"));
        try {
            return objectMapper.treeToValue(dataNode, CreatedUserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map response", e);
        }
    }

    public Response deleteUser(Integer userId, User user) {
        Map<String, List<Integer>> body = Map.of("ids", List.of(userId));
        RequestSpecification spec = ApiRequestBuilder.withBody(newRequest(user), body);
        return send(Method.DELETE, BASE_USER_ADMIN_URL, spec);
    }

    public void deleteUserAndVerifySuccess(Integer userId, User user) {
        verifySuccess(deleteUser(userId, user));
    }
}