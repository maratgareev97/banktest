package com.banktest.banktest.dto;

import lombok.Data;

@Data
public class UpdateEmailRequest {
    private String oldEmail;
    private String newEmail;
}