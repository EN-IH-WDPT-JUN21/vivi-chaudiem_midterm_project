package com.ironhack.midtermproject.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.Address;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.Role;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AccountHolderRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Role role = new Role("ACCOUNT_HOLDER");
        LocalDate dateOfBirth = LocalDate.parse("1995-01-01");
        Address primaryAddress = new Address("Street address", "12345", "Berlin");
        AccountHolder accountHolderOne = new AccountHolder("jane", "password123", role, dateOfBirth, primaryAddress,
                null);
        AccountHolder accountHolderTwo = new AccountHolder("john", "password123", role, dateOfBirth, primaryAddress,
                null);
        List<AccountHolder> accountHolders = accountHolderRepository.saveAll(List.of(accountHolderOne, accountHolderTwo));

        Owner primaryOwnerOne = new Owner("Jane");
        Owner primaryOwnerTwo = new Owner("John");
        LocalDateTime creationDate = LocalDateTime.now();

        List<Savings> savings = savingsRepository.saveAll(List.of(
                new Savings(BigDecimal.valueOf(100), primaryOwnerOne, null,
                        creationDate, accountHolderOne, "secretkey123", Status.ACTIVE,
                        BigDecimal.valueOf(1000), BigDecimal.valueOf(0.0025))
//                new Savings(BigDecimal.valueOf(200), primaryOwnerTwo, null,
//                        creationDate, accountHolderTwo, "secretkey123", Status.ACTIVE,
//                        BigDecimal.valueOf(1000), BigDecimal.valueOf(0.0025))
        ));
    }

    @AfterEach
    void tearDown() {
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void findAll_listOfSavings() throws Exception {
        MvcResult result = mockMvc.perform(get("/savings")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jane"));
//        assertTrue(result.getResponse().getContentAsString().contains("John"));
    }


    /*
    @Test
    void findAll_withRegisters_listOfRegisters() throws Exception {
        MvcResult result = mockMvc.perform(get("/doctors")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Alonso Flores"));
        assertTrue(result.getResponse().getContentAsString().contains("German Ruiz"));
        assertTrue(result.getResponse().getContentAsString().contains("Paolo Rodriguez"));
    }
     */

    @Test
    void findById() {
    }

    @Test
    void store() {
    }

    @Test
    void addInterest() {
    }

    @Test
    void update() {
    }

    @Test
    void getSavingsByIdAndPrimaryOwner() {
    }

    @Test
    void getSavingsByIdAndSecondaryOwner() {
    }
}