package com.ironhack.midtermproject.utils;

import com.ironhack.midtermproject.dao.LoginData.Admin;
import com.ironhack.midtermproject.dao.LoginData.Role;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.ironhack.midtermproject.utils.PasswordUtil.encryptedPassword;

// Will be executed when running the app in order to have a default admin user

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    AdminRepository adminRepository;

    @Override
    public void run(String...args) throws Exception {
        Role adminRole = new Role("ADMIN");
        Admin adminUser = new Admin("admin", encryptedPassword("admin123"), adminRole, "Admin User");
        adminRepository.save(adminUser);
    }
}
