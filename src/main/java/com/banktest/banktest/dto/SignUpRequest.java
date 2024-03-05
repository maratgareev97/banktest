package com.banktest.banktest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class SignUpRequest {
    private String fullName;
    private String login;
    private String password;
    private Set<String> phone;
    private Set<String> email;
    private LocalDate birthDate;
    private BigDecimal initialBalance; // Добавляем поле для начальной суммы

}
