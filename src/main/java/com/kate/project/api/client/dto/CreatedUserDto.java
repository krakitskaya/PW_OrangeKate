package com.kate.project.api.client.dto;

import com.kate.project.api.client.entities.Employee;
import lombok.Data;

@Data
public class CreatedUserDto {
    private int id;
    private String userName;
    private Boolean deleted;
    private Boolean status;
    private Employee employee;
    private UserRole userRole;

    @Data
    public static class UserRole {
        private Integer id;
        private String name;
        private String displayName;
    }
}