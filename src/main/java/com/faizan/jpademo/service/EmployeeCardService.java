package com.faizan.jpademo.service;

import com.faizan.jpademo.dto.EmployeeCardDTO;
import com.faizan.jpademo.entity.Employee;
import com.faizan.jpademo.entity.EmployeeCard;
import com.faizan.jpademo.repository.EmployeeCardRepository;
import com.faizan.jpademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeCardService {

    @Autowired
    private EmployeeCardRepository employeeCardRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<EmployeeCardDTO> getAllEmployeeCards() {
        List<EmployeeCard> cards = employeeCardRepository.findAll();
        return cards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeCardDTO> getAllEmployeeCardsWithEmployees() {
        List<EmployeeCard> cards = employeeCardRepository.findAll();
        return cards.stream()
                .map(this::convertToDTOWithEmployee)
                .collect(Collectors.toList());
    }

    public EmployeeCardDTO createEmployeeCard(EmployeeCardDTO cardDTO) {
        EmployeeCard card = convertToEntity(cardDTO);
        EmployeeCard savedCard = employeeCardRepository.save(card);
        return convertToDTOWithEmployee(savedCard);
    }

    @Transactional(readOnly = true)
    public EmployeeCardDTO getEmployeeCardById(Long id) {
        return employeeCardRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public EmployeeCardDTO getEmployeeCardWithEmployee(Long id) {
        return employeeCardRepository.findByIdWithEmployee(id)
                .map(this::convertToDTOWithEmployee)
                .orElse(null);
    }

    // Conversion methods
    private EmployeeCardDTO convertToDTO(EmployeeCard card) {
        EmployeeCardDTO dto = new EmployeeCardDTO();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setIssueDate(card.getIssueDate());
        dto.setExpiryDate(card.getExpiryDate());
        dto.setCardType(card.getCardType());
        return dto;
    }

    private EmployeeCardDTO convertToDTOWithEmployee(EmployeeCard card) {
        EmployeeCardDTO dto = convertToDTO(card);

        if (card.getEmployee() != null) {
            dto.setEmployee(new com.faizan.jpademo.dto.EmployeeDTO(
                    card.getEmployee().getId(),
                    card.getEmployee().getName(),
                    card.getEmployee().getEmail(),
                    card.getEmployee().getPosition()
            ));
        }

        return dto;
    }

    private EmployeeCard convertToEntity(EmployeeCardDTO dto) {
        EmployeeCard card = new EmployeeCard();
        card.setId(dto.getId());
        card.setCardNumber(dto.getCardNumber());
        card.setIssueDate(dto.getIssueDate());
        card.setExpiryDate(dto.getExpiryDate());
        card.setCardType(dto.getCardType());

        // Set employee if provided
        if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployee().getId())
                    .orElse(null);
            card.setEmployee(employee);
        }

        return card;
    }
}