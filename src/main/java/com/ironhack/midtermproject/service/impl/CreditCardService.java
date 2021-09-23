package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.TransactionType;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.repository.TransactionRepository;
import com.ironhack.midtermproject.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ironhack.midtermproject.service.impl.SavingsService.calculateTimeDifference;

@Service
public class CreditCardService implements ICreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

//    public CreditCard store(CreditCard creditCard) {
//        Optional<Account> optionalCreditCard = creditCardRepository.findById(creditCard.getId());
//        if(optionalCreditCard.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Credit card account with id " + creditCard.getId() + "already exists.");
//
//        return creditCardRepository.save(creditCard);
//    }

    public void addInterest(Long id) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(id);

        if(!optionalCreditCard.isPresent()) return;
        CreditCard creditCard = optionalCreditCard.get();

        // Add interest rate if it has been 1 month or longer since interest was added
        boolean interestAddedOneMonthOrLonger = interestAddedOneMonthOrLonger(id);

        // Add interest rate if it has been 1 month or longer since Account creation and if the account has never gotten any interest rate
        int timeDifference = calculateTimeDifference(creditCard.getCreationDate(), LocalDate.now());
        boolean hasNeverGottenAnyInterest = hasNeverGottenAnyInterest(id);

        if(interestAddedOneMonthOrLonger || (timeDifference > 30 && hasNeverGottenAnyInterest)) {
            BigDecimal currentBalance = creditCard.getBalance();
            BigDecimal interestToAdd = creditCard.getInterestRate().add(BigDecimal.ONE);
            BigDecimal newBalance = currentBalance.multiply(interestToAdd);

            creditCard.setBalance(newBalance);
            creditCardRepository.save(creditCard);

            Transaction transaction = new Transaction(TransactionType.INTEREST, AccountType.CREDITCARD, creditCard.getId(), null, null, currentBalance.multiply(creditCard.getInterestRate()), LocalDate.now());
            transactionRepository.save(transaction);
        }

    }

    public boolean interestAddedOneMonthOrLonger(Long id) {
        List<Transaction> transactionList = transactionRepository.findByAccountOneIdAndAccountOneType(id, AccountType.CREDITCARD);
        for(Transaction transaction : transactionList) {
            if(transaction.getTransactionType() == TransactionType.INTEREST && transaction.getAccountOneType() == AccountType.CREDITCARD) {
                int timeDifference = calculateTimeDifference(transaction.getTransactionDate(), LocalDate.now());
                if(timeDifference > 30) return true;
            }
        }
        return false;
    }

    public boolean hasNeverGottenAnyInterest(Long id) {
        List<Transaction> transactionList = transactionRepository.findByAccountOneIdAndAccountOneType(id, AccountType.CREDITCARD);
        if(transactionList.isEmpty()) return true;
        return false;
    }


}
