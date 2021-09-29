package com.ironhack.midtermproject.repository.LoginDataRepositories;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.Role;
import com.ironhack.midtermproject.dao.LoginData.ThirdParty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThirdPartyRepositoryTest {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private ThirdParty thirdParty;

    @BeforeEach
    void setUp() {
        Role role = new Role("ACCOUNT_HOLDER");
        thirdParty = new ThirdParty("bob", "123", role, "Bob", "abcd");
        thirdPartyRepository.save(thirdParty);
    }

    @AfterEach
    void tearDown() {
        thirdPartyRepository.deleteAll();
    }

    @Test
    void findById_thirdParty() {
        Optional<ThirdParty> thirdPartyOptional = thirdPartyRepository.findById(thirdParty.getId());
        assertTrue(thirdPartyOptional.isPresent());
    }
}