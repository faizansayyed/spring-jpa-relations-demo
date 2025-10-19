package com.faizan.jpademo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status; // ACTIVE, COMPLETED, ON_HOLD

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees = new HashSet<>();

    // Convenience method to add employee
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getProjects().add(this);
    }

    // Convenience method to remove employee
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getProjects().remove(this);
    }

    // Convenience constructor
    public Project(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

}
