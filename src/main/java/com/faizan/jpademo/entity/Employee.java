package com.faizan.jpademo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id") // foreign key column
    private Department department;

    // One-to-One relationship with EmployeeCard (1:1) - Inverse side
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmployeeCard employeeCard;

    // Many-to-Many relationship with Project (M:N) - Inverse side
    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    // Convenience method to set employee card
    public void setEmployeeCard(EmployeeCard card) {
        this.employeeCard = card;
        if (card != null) {
            card.setEmployee(this);
        }
    }

    // Convenience method to add project
    public void addProject(Project project) {
        this.projects.add(project);
        project.getEmployees().add(this);
    }

    // Convenience method to remove project
    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getEmployees().remove(this);
    }

    // Convenience constructor
    public Employee(String name, String email, String position) {
        this.name = name;
        this.email = email;
        this.position = position;
    }
}