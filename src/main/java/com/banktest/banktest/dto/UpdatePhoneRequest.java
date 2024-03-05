package com.banktest.banktest.dto;

import lombok.Data;

@Data
public class UpdatePhoneRequest {
    private String oldPhone;
    private String newPhone;
}