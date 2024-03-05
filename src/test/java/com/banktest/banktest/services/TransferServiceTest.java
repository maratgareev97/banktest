package com.banktest.banktest.services;

import com.banktest.banktest.models.BankAccount;
import com.banktest.banktest.models.User;
import com.banktest.banktest.repository.BankAccountRepository;
import com.banktest.banktest.repository.UserRepository;
import com.banktest.banktest.services.impl.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TransferServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    void testTransfer() {
        // Arrange
        User fromUser = new User();
        fromUser.setLogin("fromUser");
        BankAccount fromAccount = new BankAccount();
        fromAccount.setBalance(new BigDecimal("100"));
        fromUser.setBankAccount(fromAccount);

        User toUser = new User();
        toUser.setLogin("toUser");
        BankAccount toAccount = new BankAccount();
        toAccount.setBalance(new BigDecimal("50"));

        Mockito.when(userRepository.findByLogin("fromUser")).thenReturn(Optional.of(fromUser));
        Mockito.when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(toAccount));

        // Act
        transferService.transfer("fromUser", 1L, new BigDecimal("30"));

        // Assert
        verify(bankAccountRepository, Mockito.times(2)).save(any(BankAccount.class));
    }


    @Test
    void testTransferInsufficientFunds() {
        // Arrange
        User fromUser = new User();
        fromUser.setLogin("fromUser");
        BankAccount fromAccount = new BankAccount();
        fromAccount.setBalance(new BigDecimal("100"));

        User toUser = new User();
        toUser.setLogin("toUser");
        BankAccount toAccount = new BankAccount();
        toAccount.setBalance(new BigDecimal("50"));

        Mockito.when(userRepository.findByLogin("fromUser")).thenReturn(Optional.of(fromUser));
        Mockito.when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(toAccount));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> transferService.transfer("fromUser", 1L, new BigDecimal("150")));
    }

}
