package com.banktest.banktest.services.impl;

import com.banktest.banktest.models.BankAccount;
import com.banktest.banktest.models.User;
import com.banktest.banktest.repository.BankAccountRepository;
import com.banktest.banktest.repository.UserRepository;
import com.banktest.banktest.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void transfer(String fromUserLogin, Long toAccountId, BigDecimal amount) {
        User fromUser = userRepository.findByLogin(fromUserLogin).orElseThrow(() -> new RuntimeException("User not found"));
        BankAccount fromAccount = fromUser.getBankAccount();
        BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("Account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
    }
}
