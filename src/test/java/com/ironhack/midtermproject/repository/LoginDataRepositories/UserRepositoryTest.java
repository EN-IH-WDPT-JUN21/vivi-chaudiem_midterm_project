package com.ironhack.midtermproject.repository.LoginDataRepositories;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.Role;
import com.ironhack.midtermproject.dao.LoginData.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        Role role = new Role("ACCOUNT_HOLDER");
        user = new User("bob", "123", role);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsername_user() {
        Optional<User> userOptional = userRepository.findById(user.getId());
        assertTrue(userOptional.isPresent());
    }
}