package com.banktest.banktest.services.impl;

import com.banktest.banktest.dto.JwtAuthenticationResponse;
import com.banktest.banktest.dto.RefreshTokenRequest;
import com.banktest.banktest.dto.SignInRequest;
import com.banktest.banktest.dto.SignUpRequest;
import com.banktest.banktest.models.BankAccount;
import com.banktest.banktest.models.User;
import com.banktest.banktest.repository.UserRepository;
import com.banktest.banktest.services.AuthenticationService;
import com.banktest.banktest.services.JWTservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTservice jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    @Override
    public User signup(SignUpRequest signUpRequest) {
        User user = new User();

        user.setLogin(signUpRequest.getLogin());
        user.setFullName(signUpRequest.getFullName());
        user.setBirthDate(signUpRequest.getBirthDate());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

//        user.setPhones(new HashSet<>(Arrays.asList(signUpRequest.getPhone())));
//        user.setEmails(new HashSet<>(Arrays.asList(signUpRequest.getEmail())));
        user.setPhones(signUpRequest.getPhone());
        user.setEmails(signUpRequest.getEmail());


// Инициализация банковского аккаунта с начальной суммой
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(signUpRequest.getInitialBalance()); // Установка начальной суммы
        user.setBankAccount(bankAccount);
        logger.info("Пользователь создан");

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getLogin(),
                signInRequest.getPassword()));
        var user = userRepository.findByLogin(signInRequest.getLogin()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByLogin(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;

        }
        return null;
    }
}
