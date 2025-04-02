package com.kate.project.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.kate.project.api.client.dto.CreatedUserDto;
import com.kate.project.api.client.dto.UserRequestDto;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class UserAdminApiClient extends BaseApiClient {
    private final static String BASE_USERS_URI = "/admin/users";

    public UserAdminApiClient(User user) {
        super(user);
    }

    @SneakyThrows
    public CreatedUserDto createNewUser(UserRequestDto userRequestDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = sendRequest(Method.POST, BASE_USERS_URI, userRequestDto);
        JsonNode dataNode = objectMapper.valueToTree(response.jsonPath().get("data"));
        return objectMapper.treeToValue(dataNode,  CreatedUserDto.class);
    }

    public void deleteUserById(Integer userId) {
        Map<String, List<Integer>> requestBody = Map.of("ids", List.of(userId));
        sendRequest(Method.DELETE, BASE_USERS_URI, requestBody);
    }
}