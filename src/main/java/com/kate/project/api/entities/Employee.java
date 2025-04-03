package com.kate.project.api.entities;

import lombok.Data;

@Data
public class Employee {
    private int empNumber;
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer terminationId;
}