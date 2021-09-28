package com.ironhack.midtermproject.controller.impl;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
class TransactionControllerTest {
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
                new Transaction(TransactionType.INTEREST, AccountType.SAVINGS, 1L,
                        null, null, value2, transactionDate)
        ));
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    void findAll_listOfTransactions() throws Exception {
        MvcResult result = mockMvc.perform(get("/transactions")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(String.valueOf(TransactionType.MONEY_TRANSFER)));
        assertTrue(result.getResponse().getContentAsString().contains(String.valueOf(TransactionType.INTEREST)));
    }

    @Test
    void findByTransactionId_correct() throws Exception {
        Transaction transaction = transactionList.get(0);
        long id = transaction.getId();
        MvcResult result = mockMvc.perform(get("/transactions/" + id)).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(String.valueOf(TransactionType.MONEY_TRANSFER)));
    }

    @Test
    void findByAccountOneId_correct() throws Exception {
        Transaction transaction = transactionList.get(0);
        long id = transaction.getId();
        String transactionType = transaction.getTransactionType().toString();
        MvcResult result = mockMvc.perform(get("/transactions/savings/" + id)).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(transactionType));
    }

    @Test
    void transferMoney() {
    }

    @Test
    void transferMoneyThirdParty() {
    }
}