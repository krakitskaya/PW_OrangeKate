package com.kate.project.api.client.dto;

import lombok.Data;

@Data
public class PostNewBuzzRequestDto {
    private final String text;
    private final String type = "text";

    public PostNewBuzzRequestDto(String text) {
        this.text = text;
    }
}