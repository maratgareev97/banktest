package com.banktest.banktest.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private Long toAccountId;
    private BigDecimal amount;
}