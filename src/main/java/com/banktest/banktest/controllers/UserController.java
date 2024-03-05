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
        return ResponseEntity.ok("проверка");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(Authentication authentication, @RequestBody TransferRequest transferRequest) {
        String username = authentication.getName();
        transferService.transfer(username, transferRequest.getToAccountId(), transferRequest.getAmount());
        return ResponseEntity.ok("Транзакция проведена");
    }

    @PostMapping("/updatePhone")
    public ResponseEntity<String> updatePhone(Authentication authentication, @RequestBody UpdatePhoneRequest updatePhoneRequest) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Юзер не найден"));
        userService.updatePhone(user, updatePhoneRequest.getOldPhone(), updatePhoneRequest.getNewPhone());
        return ResponseEntity.ok("Телефон обновлен");
    }

    @PostMapping("/updateEmail")
    public ResponseEntity<String> updateEmail(Authentication authentication, @RequestBody UpdateEmailRequest updateEmailRequest) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Юзер не найден"));
        userService.updateEmail(user, updateEmailRequest.getOldEmail(), updateEmailRequest.getNewEmail());
        return ResponseEntity.ok("Email обновлен");
    }

    @PostMapping("/deletePhone")
    public ResponseEntity<String> deletePhone(Authentication authentication, @RequestParam String phone) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Юзер не найден"));
        userService.deletePhone(user, phone);
        return ResponseEntity.ok("Phone удален");
    }

    @PostMapping("/deleteEmail")
    public ResponseEntity<String> deleteEmail(Authentication authentication, @RequestParam String email) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Юзер не найден"));
        userService.deleteEmail(user, email);
        return ResponseEntity.ok("Email удален");
    }
}
