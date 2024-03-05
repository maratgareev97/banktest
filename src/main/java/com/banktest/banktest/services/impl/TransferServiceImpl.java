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
    private final BankAccountRepository bankAccountRepository;

    private final UserRepository userRepository;

    public TransferServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void transfer(String fromUserLogin, Long toAccountId, BigDecimal amount) {
        User fromUser = userRepository.findByLogin(fromUserLogin).orElseThrow(() -> new RuntimeException("Юзер не найден"));
        BankAccount fromAccount = fromUser.getBankAccount();
        BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("Юзер не найден"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
    }
}
