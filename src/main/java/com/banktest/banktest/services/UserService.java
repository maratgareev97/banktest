package com.banktest.banktest.services;

import com.banktest.banktest.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {
    public UserDetailsService userDetailsService();

    public Optional<User> findByUsername(String username);
    public boolean updatePhone(User user, String oldPhone, String newPhone);
    public boolean updateEmail(User user, String oldEmail, String newEmail);
}
