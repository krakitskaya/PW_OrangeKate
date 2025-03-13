package com.kate.project.api.client.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private Boolean status;
    private Integer userRoleId;
    private Integer empNumber;
}