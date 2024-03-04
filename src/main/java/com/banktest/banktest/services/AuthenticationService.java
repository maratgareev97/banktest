package com.banktest.banktest.services;

import com.banktest.banktest.dto.JwtAuthenticationResponse;
import com.banktest.banktest.dto.RefreshTokenRequest;
import com.banktest.banktest.dto.SignInRequest;
import com.banktest.banktest.dto.SignUpRequest;
import com.banktest.banktest.models.User;

public interface AuthenticationService {
    public User signup(SignUpRequest signUpRequest);
    public JwtAuthenticationResponse signin(SignInRequest signInRequest);
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
