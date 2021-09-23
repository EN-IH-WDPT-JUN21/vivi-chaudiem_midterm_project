package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.enums.CheckingType;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class CheckingService implements ICheckingService {

    @Autowired
    private CheckingRepository checkingRepository;

    public Checking store(Checking checking) {
        // Create a student checking account if the account holder is younger than 24
        int age = calculateAge(checking.getAccountHolder().getDateOfBirth(), LocalDate.now());

        if(age < 24) {
            checking.setCheckingType(CheckingType.STUDENT_CHECKING);
            checking.setMinimumBalance(null);
            checking.setMonthlyMaintenanceFee(null);
        }
        return checkingRepository.save(checking);
    }

    // Helper method to calculate the age of the account holder
    public static int calculateAge(LocalDate dateOfBirth, LocalDate currentDate) {
        if(dateOfBirth != null && currentDate != null) {
            return Period.between(dateOfBirth, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public void update(Long id, Checking checking) {
        Optional<Checking> optionalChecking = checkingRepository.findById(id);
        if(optionalChecking.isPresent()) {
            checking.setId(optionalChecking.get().getId());
            checkingRepository.save(checking);
        }
    }

    public void updateBalance(Long id, BigDecimal balance) {
        Optional<Checking> optionalChecking = checkingRepository.findById(id);
        if(optionalChecking.isPresent()) {
            optionalChecking.get().setBalance(balance);
            checkingRepository.save(optionalChecking.get());
        }
    }
}
