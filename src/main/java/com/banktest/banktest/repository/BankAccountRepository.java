package com.banktest.banktest.repository;

import com.banktest.banktest.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    // Здесь можно добавить дополнительные методы запросов, если они потребуются
}