package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class CheckingService {
    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    // DTO instead
    public <T extends Account> T store(T checking) {
        Optional<Account> optionalChecking = checkingRepository.findById(checking.getId());
        if(optionalChecking.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Checking account with id " + checking.getId() + "already exists.");

        Optional<Account> optionalStudentChecking = studentCheckingRepository.findById(checking.getId());
        if(optionalStudentChecking.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Student checking account with id " + checking.getId() + "already exists.");

        // Create a student checking account if the account holder is younger than 24
        int age = calculateAge(checking.getAccountHolder().getDateOfBirth(), LocalDate.now());
        if(age < 24) {
            return studentCheckingRepository.save(checking);
        } else {
            return checkingRepository.save(checking);
        }
    }

    // Helper method to calculate the age of the account holder
    public static int calculateAge(LocalDate dateOfBirth, LocalDate currentDate) {
        if(dateOfBirth != null && currentDate != null) {
            return Period.between(dateOfBirth, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
