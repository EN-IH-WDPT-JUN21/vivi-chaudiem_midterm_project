package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.*;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.User;
import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.TransactionType;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AccountHolderRepository;
import com.ironhack.midtermproject.repository.LoginDataRepositories.UserRepository;
import com.ironhack.midtermproject.repository.TransactionRepository;
import com.ironhack.midtermproject.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public void transferMoney(String accountType, String value, String primaryOwnerString, String secondaryOwnerString, Long accountId) {
        // Transform input values
        Owner primaryOwner = new Owner(primaryOwnerString);
        Owner secondaryOwner = new Owner(secondaryOwnerString);
        Money valueAsMoney = new Money(BigDecimal.valueOf(Long.parseLong(value)));
        BigDecimal amount = valueAsMoney.getAmount();

        // Get username from sender
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentUser);
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByUserId(optionalUser.get().getId());

        // Get sender account and its new balance
        BigDecimal senderBalance = null;
        Long senderId = null;
        AccountType senderAccountType = null;

        switch(accountType.toUpperCase()) {
            case "CHECKING":
                Optional<Checking> optionalCheckingSender = checkingRepository.findByAccountHolderId(optionalAccountHolder.get().getId());
                senderBalance = optionalCheckingSender.get().getBalance().subtract(amount);
                senderAccountType = AccountType.CHECKING;
                senderId = optionalCheckingSender.get().getId();
                break;
            case "CREDITCARD":
                Optional<CreditCard> optionalCreditCardSender = creditCardRepository.findByAccountHolderId(optionalAccountHolder.get().getId());
                senderBalance = optionalCreditCardSender.get().getBalance().subtract(amount);
                senderAccountType = AccountType.CREDITCARD;
                senderId = optionalCreditCardSender.get().getId();
                break;
            case "SAVINGS":
                Optional<Savings> optionalSavingsSender = savingsRepository.findByAccountHolderId(optionalAccountHolder.get().getId());
                senderBalance = optionalSavingsSender.get().getBalance().subtract(amount);
                senderAccountType = AccountType.SAVINGS;
                senderId = optionalSavingsSender.get().getId();
                break;
        }

        // Get recipient account by verifying all possibilities and its new balance
        BigDecimal recipientBalance = null;

        // Case if it's a checking account
        Optional<Checking> checkingOptionalRecipient = null;
        checkingOptionalRecipient = checkingRepository.findByIdAndPrimaryOwner(accountId, primaryOwner);
        if(!checkingOptionalRecipient.isPresent()) {
            checkingOptionalRecipient = checkingRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner);
        }
        if(!checkingOptionalRecipient.isPresent()) {
            return;
        } else {
            recipientBalance = checkingOptionalRecipient.get().getBalance().add(amount);
            // Update both accounts
            checkingService.updateBalance(senderId, senderBalance);
            checkingService.updateBalance(checkingOptionalRecipient.get().getId(), recipientBalance);
            // Add the transaction
            Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, senderId,
                    AccountType.CHECKING, checkingOptionalRecipient.get().getId(), valueAsMoney, LocalDate.now());
            transactionRepository.save(transaction);
        }

        // Case if it's a creditcard account
        Optional<CreditCard> creditCardOptionalRecipient = null;
        creditCardOptionalRecipient = creditCardRepository.findByIdAndPrimaryOwner(accountId, primaryOwner);
        if(!creditCardOptionalRecipient.isPresent()) {
            creditCardOptionalRecipient = creditCardRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner);
        }
        if(!creditCardOptionalRecipient.isPresent()) {
            return;
        } else {
            recipientBalance = creditCardOptionalRecipient.get().getBalance().add(amount);
            // Update both accounts
            creditCardService.updateBalance(senderId, senderBalance);
            creditCardService.updateBalance(creditCardOptionalRecipient.get().getId(), recipientBalance);
            // Add the transaction
            Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, senderId,
                    AccountType.CHECKING, creditCardOptionalRecipient.get().getId(), valueAsMoney, LocalDate.now());
            transactionRepository.save(transaction);
        }

    }

//    public void transferMoney(String value, String senderAccount, Long senderAccountId, String recipientAccount, Long recipientAccountId) {
//        Account sender = null;
//        Account recipient = null;
//        AccountType senderAccountType = null;
//        AccountType recipientAccountType = null;
//        BigDecimal minimumBalanceSender = null;
//        BigDecimal minimumBalanceRecipient = null;
//
//        // Determine the specific sender account
//        switch(senderAccount.toUpperCase()) {
//            case "CHECKING":
//                Optional<Checking> checkingOptional = checkingRepository.findById(senderAccountId);
//                if(checkingOptional.isPresent()) {
//                    sender = checkingOptional.get();
//                    senderAccountType = AccountType.CHECKING;
//                }
//                break;
//
//            case "SAVINGS":
//                Optional<Savings> savingsOptional = savingsRepository.findById(senderAccountId);
//                if(savingsOptional.isPresent()) {
//                    sender = savingsOptional.get();
//                    senderAccountType = AccountType.SAVINGS;
//                }
//                break;
//
//            case "CREDITCARD":
//                Optional<CreditCard> creditCardOptional = creditCardRepository.findById(senderAccountId);
//                if(creditCardOptional.isPresent()) {
//                    sender = creditCardOptional.get();
//                    senderAccountType = AccountType.CREDITCARD;
//                }
//                break;
//        }
//
//        // Determine the specific recipient account
//        switch(recipientAccount.toUpperCase()) {
//            case "CHECKING":
//                Optional<Checking> checkingOptional = checkingRepository.findById(recipientAccountId);
//                if(checkingOptional.isPresent()) {
//                    recipient = checkingOptional.get();
//                    recipientAccountType = AccountType.CHECKING;
//                }
//                break;
//
//            case "SAVINGS":
//                Optional<Savings> savingsOptional = savingsRepository.findById(recipientAccountId);
//                if(savingsOptional.isPresent()) {
//                    recipient = savingsOptional.get();
//                    recipientAccountType = AccountType.SAVINGS;
//                }
//                break;
//
//            case "CREDITCARD":
//                Optional<CreditCard> creditCardOptional = creditCardRepository.findById(recipientAccountId);
//                if(creditCardOptional.isPresent()) {
//                    recipient = creditCardOptional.get();
//                    recipientAccountType = AccountType.CREDITCARD;
//                }
//                break;
//        }
//
//        // Calculate new balances
//        Money valueAsMoney = new Money(BigDecimal.valueOf(Long.parseLong(value)));
//        BigDecimal amount = valueAsMoney.getAmount();
//        BigDecimal senderBalance = sender.getBalance().subtract(amount);
//
//        BigDecimal recipientBalance = recipient.getBalance().add(amount);
//
//        // Update the balances
//        switch(senderAccountType) {
//            case CHECKING:
//                checkingService.updateBalance(sender.getId(), senderBalance);
//                break;
//            case SAVINGS:
//                savingsService.updateBalance(sender.getId(), senderBalance);
//                break;
//            case CREDITCARD:
//                creditCardService.updateBalance(sender.getId(), senderBalance);
//                break;
//        }
//
//        switch(recipientAccountType) {
//            case CHECKING:
//                checkingService.updateBalance(recipient.getId(), recipientBalance);
//                break;
//            case SAVINGS:
//                savingsService.updateBalance(recipient.getId(), recipientBalance);
//                break;
//            case CREDITCARD:
//                creditCardService.updateBalance(recipient.getId(), recipientBalance);
//                break;
//        }
//
//        Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, sender.getId(), recipientAccountType, recipient.getId(), valueAsMoney, LocalDate.now());
//        transactionRepository.save(transaction);
//    }
}
