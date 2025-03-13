package com.kate.project.api.client.dto;

import lombok.Data;

@Data
public class CreatedUserDto {
    private int id;
    private String userName;
    private boolean deleted;
    private boolean status;
    private Employee employee;
    private UserRole userRole;

    @Data
    public static class Employee {
        private int empNumber;
        private String employeeId;
        private String firstName;
        private String middleName;
        private String lastName;
        private Integer terminationId;
    }

    @Data
    public static class UserRole {
        private int id;
        private String name;
        private String displayName;
    }
}