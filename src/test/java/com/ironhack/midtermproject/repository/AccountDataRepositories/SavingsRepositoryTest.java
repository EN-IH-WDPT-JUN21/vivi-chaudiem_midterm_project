package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.Role;
import com.ironhack.midtermproject.dao.utils.Address;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SavingsRepositoryTest {
    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private Savings savings;

    @BeforeEach
    void setUp() {
        Role role = new Role("ACCOUNT_HOLDER");
        LocalDate dateOfBirth = LocalDate.parse("1995-01-01");
        Address primaryAddress = new Address("Street address", "12345", "Berlin");
        AccountHolder accountHolderOne = new AccountHolder("jane", "password123", role, dateOfBirth, primaryAddress,
                null);
        accountHolderRepository.save(accountHolderOne);

        Owner primaryOwnerOne = new Owner("Jane");
        Owner secondaryOwnerOne = new Owner("Jenny");
        LocalDateTime creationDate = LocalDateTime.of(2019, Month.MARCH, 28, 14, 33, 48);

        savings = new Savings(BigDecimal.valueOf(100), primaryOwnerOne, secondaryOwnerOne,
                        creationDate, accountHolderOne, "secretkey123", Status.ACTIVE,
                        BigDecimal.valueOf(1000), BigDecimal.valueOf(0.1));
        savingsRepository.save(savings);
    }

    @AfterEach
    void tearDown() {
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void findByIdAndPrimaryOwner_savings() {
        Optional<Savings> savingsOptional = savingsRepository.findByIdAndPrimaryOwner(savings.getId(), savings.getPrimaryOwner());
        assertTrue(savingsOptional.isPresent());
    }

    @Test
    void findByIdAndSecondaryOwner_savings() {
        Optional<Savings> savingsOptional = savingsRepository.findByIdAndSecondaryOwner(savings.getId(), savings.getSecondaryOwner());
        assertTrue(savingsOptional.isPresent());
    }

    @Test
    void findByAccountHolder_savings() {
        Optional<Savings> savingsOptional = savingsRepository.findByAccountHolder(savings.getAccountHolder());
        assertTrue(savingsOptional.isPresent());
    }
}