package com.banktest.banktest.repository;

import com.banktest.banktest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByLogin(String login);
    boolean existsByPhonesContains(String phone);
    boolean existsByEmailsContains(String email);
}