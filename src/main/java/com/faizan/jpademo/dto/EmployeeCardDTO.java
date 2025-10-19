package com.faizan.jpademo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeCardDTO {
    private Long id;
    private String cardNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String cardType;
    private EmployeeDTO employee;

    public EmployeeCardDTO() {}

    public EmployeeCardDTO(Long id, String cardNumber, LocalDate issueDate, LocalDate expiryDate, String cardType) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
    }
}