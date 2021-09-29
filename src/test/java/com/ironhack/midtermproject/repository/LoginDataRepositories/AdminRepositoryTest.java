package com.ironhack.midtermproject.repository.LoginDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.LoginData.Admin;
import com.ironhack.midtermproject.dao.LoginData.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminRepositoryTest {
    @Autowired
    AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    void setUp() {
        Role role = new Role("ADMIN");
        admin = new Admin("bob", "123", role, "Bob");
        adminRepository.save(admin);
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    void findById_admin() {
        Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());
        assertTrue(optionalAdmin.isPresent());
    }
}