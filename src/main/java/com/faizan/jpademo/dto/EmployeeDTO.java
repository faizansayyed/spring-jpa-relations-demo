package com.faizan.jpademo.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String position;
    private DepartmentDTO department;
    private EmployeeCardDTO employeeCard;
    private List<ProjectDTO> projects = new ArrayList<>();

    public EmployeeDTO() {}

    public EmployeeDTO(Long id, String name, String email, String position) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
    }
}