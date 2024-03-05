package com.banktest.banktest;

import com.banktest.banktest.models.BankAccount;
import com.banktest.banktest.models.User;
import com.banktest.banktest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class BankTestApplication implements CommandLineRunner {

//    @Autowired
//    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        User user = new User();
//
//        user.setLogin("admin@gmail.com");
//        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//        user.setFullName("admin");
//        user.setBirthDate(LocalDate.parse("2024-01-01"));
//        user.setPhones(new HashSet<>(Collections.singletonList("1234567890"))); // Пример телефона
//        user.setEmails(new HashSet<>(Collections.singletonList("admin@gmail.com"))); // Пример email
//
//        // Инициализация банковского аккаунта
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setBalance(new BigDecimal("10000.00")); // Пример начальной суммы
//        user.setBankAccount(bankAccount);
//        userRepository.save(user);
//
//
    }
}
