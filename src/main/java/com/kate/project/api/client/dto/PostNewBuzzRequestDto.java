package com.kate.project.api.client.dto;

import lombok.Data;

@Data
public class PostNewBuzzRequestDto {
    private final String text;
    private final String type;

    public PostNewBuzzRequestDto(String text, String type) {
        this.text = text;
        this.type = type;
    }
}