package com.banktest.banktest.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.banktest.banktest.repository.BankAccountRepository;
import com.banktest.banktest.models.BankAccount;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class BalanceUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(BalanceUpdateService.class);

    private final BankAccountRepository bankAccountRepository;

    public BalanceUpdateService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateBalances() {
        logger.info("Обновление балансов для всех счетов");
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            if (account.getInitialBalance() == null) {
                account.setInitialBalance(account.getBalance());
            }
            BigDecimal initialBalance = account.getInitialBalance();
            BigDecimal maxAllowedBalance = initialBalance.multiply(new BigDecimal("2.07"));
            BigDecimal newBalance = account.getBalance().multiply(new BigDecimal("1.05"));
            if (newBalance.compareTo(maxAllowedBalance) <= 0) {
                account.setBalance(newBalance);
            } else if (account.getBalance().compareTo(maxAllowedBalance) < 0) {
                account.setBalance(maxAllowedBalance);
            }
            bankAccountRepository.save(account);
        }
    }

}