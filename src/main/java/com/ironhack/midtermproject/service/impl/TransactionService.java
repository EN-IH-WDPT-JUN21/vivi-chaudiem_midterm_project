package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.TransactionType;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.repository.TransactionRepository;
import com.ironhack.midtermproject.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CheckingService checkingService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private SavingsService savingsService;

    public void transferMoney(String value, String senderAccount, Long senderAccountId, String recipientAccount, Long recipientAccountId) {
        Account sender = null;
        Account recipient = null;
        AccountType senderAccountType = null;
        AccountType recipientAccountType = null;

        // Determine the specific sender account
        switch(senderAccount.toUpperCase()) {
            case "CHECKING":
                Optional<Checking> checkingOptional = checkingRepository.findById(senderAccountId);
                if(checkingOptional.isPresent()) {
                    sender = checkingOptional.get();
                    senderAccountType = AccountType.CHECKING;
                }
                break;

            case "SAVINGS":
                Optional<Savings> savingsOptional = savingsRepository.findById(senderAccountId);
                if(savingsOptional.isPresent()) {
                    sender = savingsOptional.get();
                    senderAccountType = AccountType.SAVINGS;
                }
                break;

            case "CREDITCARD":
                Optional<CreditCard> creditCardOptional = creditCardRepository.findById(senderAccountId);
                if(creditCardOptional.isPresent()) {
                    sender = creditCardOptional.get();
                    senderAccountType = AccountType.CREDITCARD;
                }
                break;
        }

        // Determine the specific recipient account
        switch(recipientAccount.toUpperCase()) {
            case "CHECKING":
                Optional<Checking> checkingOptional = checkingRepository.findById(recipientAccountId);
                if(checkingOptional.isPresent()) {
                    recipient = checkingOptional.get();
                    recipientAccountType = AccountType.CHECKING;
                }
                break;

            case "SAVINGS":
                Optional<Savings> savingsOptional = savingsRepository.findById(recipientAccountId);
                if(savingsOptional.isPresent()) {
                    recipient = savingsOptional.get();
                    recipientAccountType = AccountType.SAVINGS;
                }
                break;

            case "CREDITCARD":
                Optional<CreditCard> creditCardOptional = creditCardRepository.findById(recipientAccountId);
                if(creditCardOptional.isPresent()) {
                    recipient = creditCardOptional.get();
                    recipientAccountType = AccountType.CREDITCARD;
                }
                break;
        }

        // Calculate new balances
        Money valueAsMoney = new Money(BigDecimal.valueOf(Long.parseLong(value)));
        BigDecimal amount = valueAsMoney.getAmount();
        BigDecimal senderBalance = sender.getBalance().subtract(amount);
        BigDecimal recipientBalance = recipient.getBalance().add(amount);

        // Update the balances
        switch(senderAccountType) {
            case CHECKING:
                checkingService.updateBalance(sender.getId(), senderBalance);
                break;
            case SAVINGS:
                savingsService.updateBalance(sender.getId(), senderBalance);
                break;
            case CREDITCARD:
                creditCardService.updateBalance(sender.getId(), senderBalance);
                break;
        }

        switch(recipientAccountType) {
            case CHECKING:
                checkingService.updateBalance(recipient.getId(), recipientBalance);
                break;
            case SAVINGS:
                savingsService.updateBalance(recipient.getId(), recipientBalance);
                break;
            case CREDITCARD:
                creditCardService.updateBalance(recipient.getId(), recipientBalance);
                break;
        }

        Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, sender.getId(), recipientAccountType, recipient.getId(), valueAsMoney, LocalDate.now());
        transactionRepository.save(transaction);
    }
}
