package com.banktest.banktest.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
