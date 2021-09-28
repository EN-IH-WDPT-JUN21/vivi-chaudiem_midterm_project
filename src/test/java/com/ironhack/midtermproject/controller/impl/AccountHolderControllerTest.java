package com.ironhack.midtermproject.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.Role;
import com.ironhack.midtermproject.dao.utils.Address;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
class AccountHolderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<AccountHolder> accountHolderList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Role role = new Role("ACCOUNT_HOLDER");
        LocalDate dateOfBirth = LocalDate.parse("1995-01-01");
        Address primaryAddress = new Address("Street address", "12345", "Berlin");
        AccountHolder accountHolderOne = new AccountHolder("jane", "password123", role, dateOfBirth, primaryAddress,
                null);

        Role role2 = new Role("ACCOUNT_HOLDER");
        LocalDate dateOfBirth2 = LocalDate.parse("1996-02-02");
        Address primaryAddress2 = new Address("Street address2", "12346", "Paris");
        AccountHolder accountHolderTwo = new AccountHolder("john", "password123", role2, dateOfBirth2, primaryAddress2,
                null);

        accountHolderList = accountHolderRepository.saveAll(List.of(accountHolderOne, accountHolderTwo));

    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
    }

    @Test
    void findAll_listOfAccountHolders() throws Exception {
        MvcResult result = mockMvc.perform(get("/accountholder")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("jane"));
        assertTrue(result.getResponse().getContentAsString().contains("john"));
    }

    @Test
    void findById_correct() throws Exception {
        AccountHolder accountHolder = accountHolderList.get(0);
        long id = accountHolder.getId();
        MvcResult result = mockMvc.perform(get("/accountholder/" + id)).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("jane"));
    }

    @Test
    void store_newAccountHolder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Role role3 = new Role("ACCOUNT_HOLDER");
        LocalDate dateOfBirth3 = LocalDate.parse("1994-01-01");
        Address primaryAddress3 = new Address("Street address3", "55555", "London");
        AccountHolder accountHolderTwo = new AccountHolder("bob", "password123", role3, dateOfBirth3, primaryAddress3,
                null);
        accountHolderRepository.save(accountHolderTwo);

        String body = objectMapper.writeValueAsString(accountHolderTwo);
        MvcResult result = mockMvc.perform(post("/create/accountholder").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("bob"));
    }

}