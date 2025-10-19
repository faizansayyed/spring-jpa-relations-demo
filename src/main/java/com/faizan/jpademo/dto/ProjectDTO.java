package com.faizan.jpademo.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private List<EmployeeDTO> employees = new ArrayList<>();

    public ProjectDTO() {}

    public ProjectDTO(Long id, String name, String description, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}