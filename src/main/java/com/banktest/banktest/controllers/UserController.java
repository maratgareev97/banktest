package com.banktest.banktest.controllers;

import com.banktest.banktest.dto.TransferRequest;
import com.banktest.banktest.dto.UpdateEmailRequest;
import com.banktest.banktest.dto.UpdatePhoneRequest;
import com.banktest.banktest.models.User;
import com.banktest.banktest.services.TransferService;
import com.banktest.banktest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final TransferService transferService;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi User");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(Authentication authentication, @RequestBody TransferRequest transferRequest) {
        String username = authentication.getName(); // Получаем имя пользователя из контекста аутентификации
        transferService.transfer(username, transferRequest.getToAccountId(), transferRequest.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }

    @PostMapping("/updatePhone")
    public ResponseEntity<String> updatePhone(Authentication authentication, @RequestBody UpdatePhoneRequest updatePhoneRequest) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updatePhone(user, updatePhoneRequest.getOldPhone(), updatePhoneRequest.getNewPhone());
        return ResponseEntity.ok("Phone updated successfully");
    }

    @PostMapping("/updateEmail")
    public ResponseEntity<String> updateEmail(Authentication authentication, @RequestBody UpdateEmailRequest updateEmailRequest) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updateEmail(user, updateEmailRequest.getOldEmail(), updateEmailRequest.getNewEmail());
        return ResponseEntity.ok("Email updated successfully");
    }
}
