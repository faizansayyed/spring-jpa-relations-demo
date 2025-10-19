package com.faizan.jpademo.repository;

import com.faizan.jpademo.entity.EmployeeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeCardRepository extends JpaRepository<EmployeeCard, Long> {

    Optional<EmployeeCard> findByCardNumber(String cardNumber);

    List<EmployeeCard> findByCardType(String cardType);

    @Query("SELECT ec FROM EmployeeCard ec JOIN FETCH ec.employee WHERE ec.id = :id")
    Optional<EmployeeCard> findByIdWithEmployee(Long id);

    boolean existsByCardNumber(String cardNumber);
}