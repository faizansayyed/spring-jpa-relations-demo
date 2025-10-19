package com.faizan.jpademo.service;

import com.faizan.jpademo.dto.DepartmentDTO;
import com.faizan.jpademo.dto.EmployeeDTO;
import com.faizan.jpademo.entity.Department;
import com.faizan.jpademo.entity.Employee;
import com.faizan.jpademo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartmentsWithEmployees() {
        List<Department> departments = departmentRepository.findAllWithEmployees();
        return departments.stream()
                .map(this::convertToDTOWithEmployees)
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentWithEmployees(Long id) {
        return departmentRepository.findByIdWithEmployees(id)
                .map(this::convertToDTOWithEmployees)
                .orElse(null);
    }

    // Conversion methods
    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        return dto;
    }

    private DepartmentDTO convertToDTOWithEmployees(Department department) {
        DepartmentDTO dto = convertToDTO(department);

        // Convert employees to DTOs
        if (department.getEmployees() != null) {
            List<EmployeeDTO> employeeDTOs = department.getEmployees().stream()
                    .map(emp -> {
                        EmployeeDTO empDTO = new EmployeeDTO();
                        empDTO.setId(emp.getId());
                        empDTO.setName(emp.getName());
                        empDTO.setEmail(emp.getEmail());
                        empDTO.setPosition(emp.getPosition());
                        return empDTO;
                    })
                    .collect(Collectors.toList());
            dto.setEmployees(employeeDTOs);
        }

        return dto;
    }

    private Department convertToEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;
    }
}