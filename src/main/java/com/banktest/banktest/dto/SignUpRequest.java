package com.banktest.banktest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SignUpRequest {
    private String fullName;
    private String login;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private BigDecimal initialBalance; // Добавляем поле для начальной суммы

}
