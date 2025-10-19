package com.faizan.jpademo.service;

import com.faizan.jpademo.dto.EmployeeDTO;
import com.faizan.jpademo.entity.Department;
import com.faizan.jpademo.entity.Employee;
import com.faizan.jpademo.repository.DepartmentRepository;
import com.faizan.jpademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployeesWithDetails() {
        List<Employee> employees = employeeRepository.findAllWithAllDetails();
        return employees.stream()
                .map(this::convertToDTOWithDetails)
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeWithDetails(Long id) {
        return employeeRepository.findByIdWithAllDetails(id)
                .map(this::convertToDTOWithDetails)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartment(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Conversion methods
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPosition(employee.getPosition());
        return dto;
    }

    private EmployeeDTO convertToDTOWithDetails(Employee employee) {
        EmployeeDTO dto = convertToDTO(employee);

        // Add department info
        if (employee.getDepartment() != null) {
            dto.setDepartment(new com.faizan.jpademo.dto.DepartmentDTO(
                    employee.getDepartment().getId(),
                    employee.getDepartment().getName(),
                    employee.getDepartment().getDescription()
            ));
        }

        // Add employee card info
        if (employee.getEmployeeCard() != null) {
            dto.setEmployeeCard(new com.faizan.jpademo.dto.EmployeeCardDTO(
                    employee.getEmployeeCard().getId(),
                    employee.getEmployeeCard().getCardNumber(),
                    employee.getEmployeeCard().getIssueDate(),
                    employee.getEmployeeCard().getExpiryDate(),
                    employee.getEmployeeCard().getCardType()
            ));
        }

        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPosition(dto.getPosition());

        // Set department if provided
        if (dto.getDepartment() != null && dto.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(dto.getDepartment().getId())
                    .orElse(null);
            employee.setDepartment(department);
        }

        return employee;
    }
}