package com.faizan.jpademo.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private List<EmployeeDTO> employees = new ArrayList<>();

    // Default constructor
    public DepartmentDTO() {}

    // Constructor without employees
    public DepartmentDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}