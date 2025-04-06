package com.kate.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuzzPostDto {
    private final String text;
    private String type = "text";

    public BuzzPostDto(String text) {
        this.text = text;
    }
}