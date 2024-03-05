package com.banktest.banktest.controllers;

import com.banktest.banktest.services.impl.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.banktest.banktest.models.User;
import com.banktest.banktest.repository.UserRepository;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/specification")
public class UserControllerSpecification {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search")
    public Page<User> searchUsers(
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "birthDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "email", required = false) String email,
            Pageable pageable) {

        Specification<User> spec = Specification.where(UserSpecification.hasFullNameLike(fullName))
                .and(UserSpecification.hasBirthDateAfter(birthDate))
                .and(UserSpecification.hasPhone(phone))
                .and(UserSpecification.hasEmail(email));

        return userRepository.findAll(spec, pageable);
    }
}
