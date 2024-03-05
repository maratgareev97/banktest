package com.banktest.banktest.services.impl;

import com.banktest.banktest.models.User;
import com.banktest.banktest.repository.UserRepository;
import com.banktest.banktest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByLogin(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Юзер не найден"));
            }
        };
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByLogin(username);
    }

    public boolean updatePhone(User user, String oldPhone, String newPhone) {
        if (!user.getPhones().contains(oldPhone)) {
            throw new RuntimeException("Старый тел не найден");
        }
        if (userRepository.existsByPhonesContains(newPhone)) {
            throw new RuntimeException("Телефон обновлен");
        }
        user.getPhones().remove(oldPhone);
        user.getPhones().add(newPhone);
        userRepository.save(user);
        return true;
    }

    public boolean updateEmail(User user, String oldEmail, String newEmail) {
        if (!user.getEmails().contains(oldEmail)) {
            throw new RuntimeException("Старый email не найден");
        }
        if (userRepository.existsByEmailsContains(newEmail)) {
            throw new RuntimeException("Email обновлен");
        }
        user.getEmails().remove(oldEmail);
        user.getEmails().add(newEmail);
        userRepository.save(user);
        return true;
    }


    public boolean deletePhone(User user, String phoneToDelete) {
        if (user.getPhones().size() > 1 && user.getPhones().contains(phoneToDelete)) {
            user.getPhones().remove(phoneToDelete);
            userRepository.save(user);
            return true;
        } else {
            throw new RuntimeException("Невозможно удалить последний номер телефона");
        }
    }

    public boolean deleteEmail(User user, String emailToDelete) {
        if (user.getEmails().size() > 1 && user.getEmails().contains(emailToDelete)) {
            user.getEmails().remove(emailToDelete);
            userRepository.save(user);
            return true;
        } else {
            throw new RuntimeException("Невозможно удалить последний email");
        }
    }


}