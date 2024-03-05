package com.banktest.banktest.services;

import java.math.BigDecimal;

public interface TransferService {
    public void transfer(String fromUserLogin, Long toAccountId, BigDecimal amount);
}
