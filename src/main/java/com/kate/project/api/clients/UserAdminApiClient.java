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

    public UserAdminApiClient(User user) {
        super(user);
    }

    public Response createUser(UserRequestDto userRequestDto) {
        RequestSpecification spec = ApiRequestBuilder.withBody(newRequest(), userRequestDto);
        return send(Method.POST, BASE_USER_ADMIN_URL, spec);
    }

    public CreatedUserDto createUserAndVerifySuccess(UserRequestDto userRequestDto) {
        Response response = createUser(userRequestDto);
        verifySuccess(response);
        JsonNode dataNode = objectMapper.valueToTree(response.jsonPath().get("data"));
        try {
            return objectMapper.treeToValue(dataNode, CreatedUserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map response", e);
        }
    }

    public Response deleteUser(Integer userId) {
        Map<String, List<Integer>> body = Map.of("ids", List.of(userId));
        RequestSpecification spec = ApiRequestBuilder.withBody(newRequest(), body);
        return send(Method.DELETE, BASE_USER_ADMIN_URL, spec);
    }

    public void deleteUserAndVerifySuccess(Integer userId) {
        verifySuccess(deleteUser(userId));
    }
}