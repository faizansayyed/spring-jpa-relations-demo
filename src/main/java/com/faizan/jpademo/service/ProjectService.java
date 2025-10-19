package com.faizan.jpademo.service;

import com.faizan.jpademo.dto.ProjectDTO;
import com.faizan.jpademo.entity.Employee;
import com.faizan.jpademo.entity.Project;
import com.faizan.jpademo.repository.EmployeeRepository;
import com.faizan.jpademo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjectsWithEmployees() {
        List<Project> projects = projectRepository.findAllWithEmployees();
        return projects.stream()
                .map(this::convertToDTOWithEmployees)
                .collect(Collectors.toList());
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProjectWithEmployees(Long id) {
        return projectRepository.findByIdWithEmployees(id)
                .map(this::convertToDTOWithEmployees)
                .orElse(null);
    }

    public ProjectDTO assignEmployeeToProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (project != null && employee != null) {
            project.addEmployee(employee);
            Project savedProject = projectRepository.save(project);
            return convertToDTOWithEmployees(savedProject);
        }

        return null;
    }

    // Conversion methods
    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        return dto;
    }

    private ProjectDTO convertToDTOWithEmployees(Project project) {
        ProjectDTO dto = convertToDTO(project);

        if (project.getEmployees() != null) {
            List<com.faizan.jpademo.dto.EmployeeDTO> employeeDTOs = project.getEmployees().stream()
                    .map(emp -> new com.faizan.jpademo.dto.EmployeeDTO(
                            emp.getId(),
                            emp.getName(),
                            emp.getEmail(),
                            emp.getPosition()
                    ))
                    .collect(Collectors.toList());
            dto.setEmployees(employeeDTOs);
        }

        return dto;
    }

    private Project convertToEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
        return project;
    }
}