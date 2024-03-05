package com.banktest.banktest.services.impl;

import org.springframework.data.jpa.domain.Specification;
import com.banktest.banktest.models.User;

import java.time.LocalDate;


public class UserSpecification {

    public static Specification<User> hasFullNameLike(String fullName) {
        return (root, query, criteriaBuilder) -> fullName == null ? null : criteriaBuilder.like(root.get("fullName"), fullName + "%");
    }

    public static Specification<User> hasBirthDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> date == null ? null : criteriaBuilder.greaterThan(root.get("birthDate"), date);
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> phone == null ? null : criteriaBuilder.equal(root.join("phones"), phone);
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> email == null ? null : criteriaBuilder.equal(root.join("emails"), email);
    }
}