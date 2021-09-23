package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.TransactionType;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.repository.TransactionRepository;
import com.ironhack.midtermproject.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class SavingsService implements ISavingsService {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void addInterest(Long id) {
        Optional<Savings> savingsAccount = savingsRepository.findById(id);

        if(!savingsAccount.isPresent()) {
            return;
        }
        Savings savings = savingsAccount.get();

        // Add interest rate if it has been 1 year or longer since interest was added
        boolean interestAddedOneYearOrLonger = interestAddedOneYearOrLonger(id);

        // Add interest rate if it has been 1 year or longer since Account creation and if the account has never gotten any interest rate
        int timeDifference = calculateTimeDifference(savings.getCreationDate(), LocalDate.now());
        boolean hasNeverGottenAnyInterest = hasNeverGottenAnyInterest(id);

        if(interestAddedOneYearOrLonger || (timeDifference > 365 && hasNeverGottenAnyInterest)) {
            BigDecimal currentBalance = savings.getBalance();
            BigDecimal interest = savings.getInterestRate();
            BigDecimal interestValue = currentBalance.multiply(interest);
            BigDecimal newBalance = currentBalance.add(interestValue);


            savings.setBalance(newBalance);
            savingsRepository.save(savings);

            Money newBalanceMoney = new Money(interestValue);
            Transaction transaction = new Transaction(TransactionType.INTEREST, AccountType.SAVINGS, savings.getId(), null, null, newBalanceMoney, LocalDate.now());
            transactionRepository.save(transaction);
        }

    }

    public boolean interestAddedOneYearOrLonger(Long id) {
        List<Transaction> transactionList = transactionRepository.findByAccountOneIdAndAccountOneType(id, AccountType.SAVINGS);
        for(Transaction transaction : transactionList) {
            if(transaction.getTransactionType() == TransactionType.INTEREST && transaction.getAccountOneType() == AccountType.SAVINGS) {
                int timeDifference = calculateTimeDifference(transaction.getTransactionDate(), LocalDate.now());
                if(timeDifference > 365) return true;
            }
        }
        return false;
    }

    public static int calculateTimeDifference(LocalDate date, LocalDate currentDate) {
        if(date != null && currentDate != null) {
            return (int) DAYS.between(date, currentDate);
        } else {
            return 0;
        }
    }

    public boolean hasNeverGottenAnyInterest(Long id) {
        List<Transaction> transactionList = transactionRepository.findByAccountOneIdAndAccountOneType(id, AccountType.SAVINGS);
        if(transactionList.isEmpty()) return true;
        return false;
    }

    public void update(Long id, Savings savings) {
        Optional<Savings> optionalSavings = savingsRepository.findById(id);
        if(optionalSavings.isPresent()) {
            savings.setId(optionalSavings.get().getId());
            savingsRepository.save(savings);
        }
    }

    public void updateBalance(Long id, BigDecimal balance) {
        Optional<Savings> optionalSavings = savingsRepository.findById(id);
        if(optionalSavings.isPresent()) {
            optionalSavings.get().setBalance(balance);
            savingsRepository.save(optionalSavings.get());
        }
    }

}
