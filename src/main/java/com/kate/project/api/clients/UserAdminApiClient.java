package com.kate.project.api.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kate.project.api.ApiRequestBuilder;
import com.kate.project.api.ApiResponse;
import com.kate.project.api.ApiResponseHelper;
import com.kate.project.api.dto.CreatedUserDto;
import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;

import java.util.List;
import java.util.Map;

public class UserAdminApiClient extends BaseApiClient {
    private static final String BASE_USER_ADMIN_URL = "/admin/users";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ApiResponse<CreatedUserDto> createUser(UserRequestDto userRequestDto, User user) {
        var spec = ApiRequestBuilder.withBody(newRequest(user), userRequestDto);
        var response = send(Method.POST, BASE_USER_ADMIN_URL, spec);

        CreatedUserDto createdUser = parseSuccessBody(response, r ->
                objectMapper.convertValue(r.jsonPath().getMap("data"), CreatedUserDto.class)
        );
        return ApiResponseHelper.wrap(response, createdUser);
    }

    public ApiResponse<Void> deleteUser(Integer userId, User user) {
        var body = Map.of("ids", List.of(userId));
        var spec = ApiRequestBuilder.withBody(newRequest(user), body);
        return sendAndWrap(Method.DELETE, BASE_USER_ADMIN_URL, spec);
    }
}