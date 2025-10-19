package com.faizan.jpademo.repository;

import com.faizan.jpademo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByNameContainingIgnoreCase(String name);

    List<Project> findByStatus(String status);

    @Query("SELECT p FROM Project p JOIN FETCH p.employees WHERE p.id = :id")
    Optional<Project> findByIdWithEmployees(Long id);

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.employees")
    List<Project> findAllWithEmployees();

    boolean existsByName(String name);
}