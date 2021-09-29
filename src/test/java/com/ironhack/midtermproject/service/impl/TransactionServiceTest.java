package com.ironhack.midtermproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermproject.dao.utils.Money;
import com.ironhack.midtermproject.dao.utils.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.TransactionType;
import com.ironhack.midtermproject.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TransactionRepository transactionRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Transaction> transactionList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Money value1 = new Money(BigDecimal.valueOf(200));
        Money value2 = new Money(BigDecimal.valueOf(10));
        LocalDateTime transactionDate = LocalDateTime.now();

        transactionList = transactionRepository.saveAll(List.of(
                new Transaction(TransactionType.MONEY_TRANSFER, AccountType.SAVINGS, 1L,
                        AccountType.CHECKING, 2L, value1, transactionDate),
                new Transaction(TransactionType.INTEREST, AccountType.SAVINGS, 2L,
                        null, null, value2, transactionDate)
        ));
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    void transferMoney() {
    }

    @Test
    void recipientExists_correct() {

    }

    @Test
    void hasEnoughMoney() {
    }

    @Test
    void belowMinimumBalance() {
    }

    @Test
    void transferMoneyThirdParty() {
    }

    @Test
    void fraudDetection() {
    }

    @Test
    void hasMoreThanTwoTransactionsPerSecond() {
    }
}