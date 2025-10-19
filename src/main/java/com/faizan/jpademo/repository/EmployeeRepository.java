package com.faizan.jpademo.repository;

import com.faizan.jpademo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by name containing (case insensitive)
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Find employees by department ID
    List<Employee> findByDepartmentId(Long departmentId);

    // Find employee by email
    Optional<Employee> findByEmail(String email);

    // Find employees by position
    List<Employee> findByPosition(String position);

    // Find employee with department details (join fetch)
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE e.id = :id")
    Optional<Employee> findByIdWithDepartment(@Param("id") Long id);

    // Find employee with card details (join fetch)
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.employeeCard WHERE e.id = :id")
    Optional<Employee> findByIdWithCard(@Param("id") Long id);

    // Find employee with all relationships (department, card, projects)
    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.employeeCard " +
            "LEFT JOIN FETCH e.projects " +
            "WHERE e.id = :id")
    Optional<Employee> findByIdWithAllDetails(@Param("id") Long id);

    // Find all employees with department details
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department")
    List<Employee> findAllWithDepartment();

    // Find all employees with all relationships
    @Query("SELECT DISTINCT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.employeeCard " +
            "LEFT JOIN FETCH e.projects")
    List<Employee> findAllWithAllDetails();

    // Count employees by department
    long countByDepartmentId(Long departmentId);

    // Check if employee exists by email
    boolean existsByEmail(String email);

    // Find employees by department name
    @Query("SELECT e FROM Employee e JOIN e.department d WHERE d.name = :departmentName")
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

    // Find employees with no department
    @Query("SELECT e FROM Employee e WHERE e.department IS NULL")
    List<Employee> findEmployeesWithNoDepartment();

    // Find employees with no card
    @Query("SELECT e FROM Employee e WHERE e.employeeCard IS NULL")
    List<Employee> findEmployeesWithNoCard();

    // Custom delete method (if needed)
    void deleteByEmail(String email);

    // Batch delete methods
    void deleteByDepartmentId(Long departmentId);

    // Custom method to check if any employee exists in department
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.department.id = :departmentId")
    boolean existsByDepartmentId(@Param("departmentId") Long departmentId);
}