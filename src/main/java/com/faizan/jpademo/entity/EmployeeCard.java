package com.faizan.jpademo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="employee_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCard {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="cad_number",unique = true,nullable = false)
    @Size(min = 5, max = 20)
    private String cardNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "card_type")
    private String cardType; // ACCESS_CARD, ID_CARD, etc.

    // One-to-One relationship with Employee
    // This is the owning side (contains the foreign key)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Convenience constructor
    public EmployeeCard(String cadNumber, LocalDate issueDate, LocalDate expiryDate, String cardType) {
        this.cardNumber = cadNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
    }
}
