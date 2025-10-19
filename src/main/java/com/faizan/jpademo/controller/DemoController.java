package com.faizan.jpademo.controller;

import com.faizan.jpademo.dto.*;
import com.faizan.jpademo.entity.*;
import com.faizan.jpademo.repository.*;
import com.faizan.jpademo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeCardService employeeCardService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCardRepository employeeCardRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // ===== DEPARTMENT ENDPOINTS =====
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO created = departmentService.createDepartment(departmentDTO);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/departments")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/with-employees")
    public List<DepartmentDTO> getAllDepartmentsWithEmployees() {
        return departmentService.getAllDepartmentsWithEmployees();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        return department != null ? ResponseEntity.ok(department) : ResponseEntity.notFound().build();
    }

    @GetMapping("/departments/{id}/with-employees")
    public ResponseEntity<DepartmentDTO> getDepartmentWithEmployees(@PathVariable Long id) {
        DepartmentDTO department = departmentService.getDepartmentWithEmployees(id);
        return department != null ? ResponseEntity.ok(department) : ResponseEntity.notFound().build();
    }

    // ===== EMPLOYEE ENDPOINTS =====
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/with-details")
    public List<EmployeeDTO> getAllEmployeesWithDetails() {
        return employeeService.getAllEmployeesWithDetails();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/employees/{id}/with-details")
    public ResponseEntity<EmployeeDTO> getEmployeeWithDetails(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeWithDetails(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/departments/{departmentId}/employees")
    public List<EmployeeDTO> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    // ===== EMPLOYEE CARD ENDPOINTS =====
    @PostMapping("/employee-cards")
    public ResponseEntity<EmployeeCardDTO> createEmployeeCard(@RequestBody EmployeeCardDTO employeeCardDTO) {
        EmployeeCardDTO created = employeeCardService.createEmployeeCard(employeeCardDTO);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/employee-cards")
    public List<EmployeeCardDTO> getAllEmployeeCards() {
        return employeeCardService.getAllEmployeeCards();
    }

    @GetMapping("/employee-cards/with-employees")
    public List<EmployeeCardDTO> getAllEmployeeCardsWithEmployees() {
        return employeeCardService.getAllEmployeeCardsWithEmployees();
    }

    @GetMapping("/employee-cards/{id}")
    public ResponseEntity<EmployeeCardDTO> getEmployeeCardById(@PathVariable Long id) {
        EmployeeCardDTO card = employeeCardService.getEmployeeCardById(id);
        return card != null ? ResponseEntity.ok(card) : ResponseEntity.notFound().build();
    }

    @GetMapping("/employee-cards/{id}/with-employee")
    public ResponseEntity<EmployeeCardDTO> getEmployeeCardWithEmployee(@PathVariable Long id) {
        EmployeeCardDTO card = employeeCardService.getEmployeeCardWithEmployee(id);
        return card != null ? ResponseEntity.ok(card) : ResponseEntity.notFound().build();
    }

    // ===== PROJECT ENDPOINTS =====
    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO created = projectService.createProject(projectDTO);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/with-employees")
    public List<ProjectDTO> getAllProjectsWithEmployees() {
        return projectService.getAllProjectsWithEmployees();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        ProjectDTO project = projectService.getProjectById(id);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }

    @GetMapping("/projects/{id}/with-employees")
    public ResponseEntity<ProjectDTO> getProjectWithEmployees(@PathVariable Long id) {
        ProjectDTO project = projectService.getProjectWithEmployees(id);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }

    @PostMapping("/projects/{projectId}/employees/{employeeId}")
    public ResponseEntity<ProjectDTO> assignEmployeeToProject(
            @PathVariable Long projectId,
            @PathVariable Long employeeId) {
        ProjectDTO project = projectService.assignEmployeeToProject(projectId, employeeId);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }

    // ===== DEMO ENDPOINTS =====
    @GetMapping("/demo/all-relationships")
    public String demonstrateAllRelationships() {
        try {
            // Clean up any existing data
            cleanUpData();

            // 1. Create Department (1:N)
            DepartmentDTO deptDTO = new DepartmentDTO();
            deptDTO.setName("Engineering");
            deptDTO.setDescription("Software Development Department");
            DepartmentDTO savedDept = departmentService.createDepartment(deptDTO);

            // 2. Create Employees (N:1)
            EmployeeDTO emp1DTO = new EmployeeDTO();
            emp1DTO.setName("John Doe");
            emp1DTO.setEmail("john.doe@company.com");
            emp1DTO.setPosition("Software Engineer");
            emp1DTO.setDepartment(savedDept);

            EmployeeDTO emp2DTO = new EmployeeDTO();
            emp2DTO.setName("Jane Smith");
            emp2DTO.setEmail("jane.smith@company.com");
            emp2DTO.setPosition("Senior Engineer");
            emp2DTO.setDepartment(savedDept);

            EmployeeDTO savedEmp1 = employeeService.createEmployee(emp1DTO);
            EmployeeDTO savedEmp2 = employeeService.createEmployee(emp2DTO);

            // 3. Create Employee Cards (1:1)
            EmployeeCardDTO card1DTO = new EmployeeCardDTO();
            card1DTO.setCardNumber("CARD001");
            card1DTO.setIssueDate(LocalDate.now());
            card1DTO.setExpiryDate(LocalDate.now().plusYears(2));
            card1DTO.setCardType("ACCESS_CARD");
            card1DTO.setEmployee(savedEmp1);

            EmployeeCardDTO card2DTO = new EmployeeCardDTO();
            card2DTO.setCardNumber("CARD002");
            card2DTO.setIssueDate(LocalDate.now());
            card2DTO.setExpiryDate(LocalDate.now().plusYears(2));
            card2DTO.setCardType("ACCESS_CARD");
            card2DTO.setEmployee(savedEmp2);

            EmployeeCardDTO savedCard1 = employeeCardService.createEmployeeCard(card1DTO);
            EmployeeCardDTO savedCard2 = employeeCardService.createEmployeeCard(card2DTO);

            // 4. Create Projects (M:N)
            ProjectDTO project1DTO = new ProjectDTO();
            project1DTO.setName("E-Commerce Platform");
            project1DTO.setDescription("Build online shopping platform");
            project1DTO.setStatus("ACTIVE");

            ProjectDTO project2DTO = new ProjectDTO();
            project2DTO.setName("Mobile App");
            project2DTO.setDescription("Develop mobile application");
            project2DTO.setStatus("ACTIVE");

            ProjectDTO savedProject1 = projectService.createProject(project1DTO);
            ProjectDTO savedProject2 = projectService.createProject(project2DTO);

            // 5. Assign employees to projects (M:N)
            projectService.assignEmployeeToProject(savedProject1.getId(), savedEmp1.getId());
            projectService.assignEmployeeToProject(savedProject1.getId(), savedEmp2.getId());
            projectService.assignEmployeeToProject(savedProject2.getId(), savedEmp1.getId());

            // 6. Fetch complete data with relationships
            DepartmentDTO finalDept = departmentService.getDepartmentWithEmployees(savedDept.getId());
            EmployeeDTO finalEmp1 = employeeService.getEmployeeWithDetails(savedEmp1.getId());
            EmployeeDTO finalEmp2 = employeeService.getEmployeeWithDetails(savedEmp2.getId());
            ProjectDTO finalProject1 = projectService.getProjectWithEmployees(savedProject1.getId());

            // Build demonstration result
            return buildDemoResult(finalDept, finalEmp1, finalEmp2, savedCard1, finalProject1, savedProject2);

        } catch (Exception e) {
            return "Error during demo: " + e.getMessage() + "\nStack Trace: " + Arrays.toString(e.getStackTrace());
        }
    }

    private void cleanUpData() {
        try {
            employeeCardRepository.deleteAllInBatch();
        } catch (Exception e) {
            employeeCardRepository.findAll().forEach(employeeCardRepository::delete);
        }

        try {
            List<Project> projects = projectRepository.findAll();
            for (Project project : projects) {
                project.getEmployees().clear();
            }
            projectRepository.saveAll(projects);
            projectRepository.deleteAllInBatch();
        } catch (Exception e) {
            projectRepository.findAll().forEach(projectRepository::delete);
        }

        try {
            employeeRepository.deleteAllInBatch();
        } catch (Exception e) {
            employeeRepository.findAll().forEach(employeeRepository::delete);
        }

        try {
            departmentRepository.deleteAllInBatch();
        } catch (Exception e) {
            departmentRepository.findAll().forEach(departmentRepository::delete);
        }
    }

    private String buildDemoResult(DepartmentDTO dept, EmployeeDTO emp1, EmployeeDTO emp2,
                                   EmployeeCardDTO card1, ProjectDTO project1, ProjectDTO project2) {
        StringBuilder result = new StringBuilder();
        result.append("=== ALL JPA RELATIONSHIPS DEMONSTRATION (WITH DTOs) ===\n\n");

        result.append("1. ONE-TO-MANY & MANY-TO-ONE (Department ↔ Employee):\n");
        result.append("   Department '").append(dept.getName())
                .append("' has ").append(dept.getEmployees().size()).append(" employees\n");
        result.append("   Employee '").append(emp1.getName())
                .append("' belongs to '").append(emp1.getDepartment().getName()).append("' department\n\n");

        result.append("2. ONE-TO-ONE (Employee ↔ EmployeeCard):\n");
        if (emp1.getEmployeeCard() != null) {
            result.append("   Employee '").append(emp1.getName())
                    .append("' has card: ").append(emp1.getEmployeeCard().getCardNumber()).append("\n");
        }
        if (card1.getEmployee() != null) {
            result.append("   Card '").append(card1.getCardNumber())
                    .append("' belongs to: ").append(card1.getEmployee().getName()).append("\n\n");
        }

        result.append("3. MANY-TO-MANY (Employee ↔ Project):\n");
        result.append("   Project '").append(project1.getName())
                .append("' has ").append(project1.getEmployees().size()).append(" employees\n");
        result.append("   Employee '").append(emp1.getName())
                .append("' works on ").append(emp1.getProjects().size()).append(" projects\n");
        result.append("   Employee '").append(emp2.getName())
                .append("' works on ").append(emp2.getProjects().size()).append(" projects\n\n");

        result.append("=== RELATIONSHIP SUMMARY ===\n");
        result.append("✓ Department → Employees: ").append(!dept.getEmployees().isEmpty()).append("\n");
        result.append("✓ Employee → Department: ").append(emp1.getDepartment() != null).append("\n");
        result.append("✓ Employee → EmployeeCard: ").append(emp1.getEmployeeCard() != null).append("\n");
        result.append("✓ EmployeeCard → Employee: ").append(card1.getEmployee() != null).append("\n");
        result.append("✓ Employee → Projects: ").append(emp1.getProjects() != null && !emp1.getProjects().isEmpty()).append("\n");
        result.append("✓ Project → Employees: ").append(project1.getEmployees() != null && !project1.getEmployees().isEmpty()).append("\n");

        result.append("\n=== DTOs WORKING PROPERLY - NO LAZYINITIALIZATIONEXCEPTION ===\n");

        return result.toString();
    }

    // Quick test endpoint
    @GetMapping("/test/dto")
    public String testDTOs() {
        DepartmentDTO dept = new DepartmentDTO(1L, "Test Dept", "Test Description");
        EmployeeDTO emp = new EmployeeDTO(1L, "Test Employee", "test@company.com", "Tester");
        EmployeeCardDTO card = new EmployeeCardDTO(1L, "TEST123", LocalDate.now(), LocalDate.now().plusYears(1), "TEST");
        ProjectDTO project = new ProjectDTO(1L, "Test Project", "Test Desc", "ACTIVE");

        return "DTOs created successfully:\n" +
                "Department: " + dept.getName() + "\n" +
                "Employee: " + emp.getName() + "\n" +
                "Card: " + card.getCardNumber() + "\n" +
                "Project: " + project.getName();
    }
}