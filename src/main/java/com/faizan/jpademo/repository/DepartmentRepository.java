package com.faizan.jpademo.repository;

import com.faizan.jpademo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Find department by exact name
    Optional<Department> findByName(String name);

    // Find departments by name containing (case insensitive)
    List<Department> findByNameContainingIgnoreCase(String name);

    // Find department with employees (join fetch)
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.id = :id")
    Optional<Department> findByIdWithEmployees(@Param("id") Long id);

    // Find all departments with employees
    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> findAllWithEmployees();

    // Find department by name with employees
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.name = :name")
    Optional<Department> findByNameWithEmployees(@Param("name") String name);

    // Check if department exists by name
    boolean existsByName(String name);

    // Find departments with no employees
    @Query("SELECT d FROM Department d WHERE d.employees IS EMPTY")
    List<Department> findDepartmentsWithNoEmployees();

    // Find departments having more than specified number of employees
    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) > :minEmployeeCount")
    List<Department> findDepartmentsWithEmployeeCountGreaterThan(@Param("minEmployeeCount") int minEmployeeCount);

    // Count departments by name pattern
    long countByNameContainingIgnoreCase(String name);

    // Custom query to get department names only
    @Query("SELECT d.name FROM Department d")
    List<String> findAllDepartmentNames();

    // Find departments with employee count
    @Query("SELECT d, SIZE(d.employees) as employeeCount FROM Department d ORDER BY employeeCount DESC")
    List<Object[]> findDepartmentsWithEmployeeCount();

    // Find top departments by employee count
    @Query("SELECT d FROM Department d ORDER BY SIZE(d.employees) DESC")
    List<Department> findTopDepartmentsByEmployeeCount();

    // Custom update method example
    @Query("UPDATE Department d SET d.description = :description WHERE d.id = :id")
    void updateDescriptionById(@Param("id") Long id, @Param("description") String description);

    // Native SQL query example
    @Query(value = "SELECT * FROM department WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    List<Department> findByNameNative(@Param("name") String name);

    // Check if any department exists (useful for initialization)
    boolean existsByIdIsNotNull();

    // Find departments with specific employee positions
    @Query("SELECT DISTINCT d FROM Department d JOIN d.employees e WHERE e.position = :position")
    List<Department> findDepartmentsWithEmployeePosition(@Param("position") String position);
}