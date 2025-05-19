package com.kate.project.api;

import com.kate.project.api.interfaces.HandledResponse;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListApiResponse<T> implements HandledResponse<List<T>> {
    private final Response response;
    private final List<T> body;
    private final ErrorResponse error;

    public ListApiResponse(Response response, Function<Map<String, Object>, T> mapper) {
        this.response = response;

        if (isSuccessful()) {
            List<Map<String, Object>> data = response.jsonPath().getList("data");
            this.body = data.stream()
                    .map(mapper)
                    .collect(Collectors.toList());
            this.error = null;
        } else {
            this.body = Collections.emptyList();
            ErrorResponse parsed = null;
            try {
                parsed = response.as(ErrorResponse.class);
            } catch (Exception ignored) {}
            this.error = parsed;
        }
    }

    @Override
    public Response originalResponse() {
        return response;
    }

    @Override
    public List<T> body() {
        return body;
    }

    @Override
    public ErrorResponse errorResponse() {
        return error;
    }
}