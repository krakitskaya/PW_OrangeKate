package com.kate.project.api.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.kate.project.api.dto.CreatedUserDto;
import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class UserAdminApiClient extends BaseApiClient implements ResponseVerifier {
    public static final String BASE_USER_ADMIN_URL = "/admin/users";

    public UserAdminApiClient(User user) {
        super(user);
    }

    public Response createUser(UserRequestDto userRequestDto) {
        return context.newRequest().withBody(userRequestDto).send(Method.POST, BASE_USER_ADMIN_URL);
    }

    public CreatedUserDto createUserAndVerifySuccess(UserRequestDto userRequestDto) {
        Response response = createUser(userRequestDto);
        verifySuccess(response);
        JsonNode dataNode = context.getObjectMapper().valueToTree(response.jsonPath().get("data"));
        try {
            return context.getObjectMapper().treeToValue(dataNode, CreatedUserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map response", e);
        }
    }

    public Response deleteUser(Integer userId) {
        Map<String, List<Integer>> body = Map.of("ids", List.of(userId));
        return context.newRequest().withBody(body).send(Method.DELETE, BASE_USER_ADMIN_URL);
    }

    public void deleteUserAndVerifySuccess(Integer userId) {
        verifySuccess(deleteUser(userId));
    }
}