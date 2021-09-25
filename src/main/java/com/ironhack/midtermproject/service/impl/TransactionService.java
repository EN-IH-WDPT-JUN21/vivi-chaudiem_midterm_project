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
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(optionalUser.get().getId());

        // Get sender account and its new balance
        BigDecimal senderBalance = null;
        Long senderId = null;
        AccountType senderAccountType = null;

        if(recipientExists(primaryOwnerString, secondaryOwnerString, accountId)) {
            switch(accountType.toUpperCase()) {
                case "CHECKING":
                    Optional<Checking> optionalCheckingSender = checkingRepository.findByAccountHolder(optionalAccountHolder.get());
                    senderBalance = optionalCheckingSender.get().getBalance().subtract(amount);
                    senderAccountType = AccountType.CHECKING;
                    senderId = optionalCheckingSender.get().getId();
                    // Update balance of sender
                    checkingService.updateBalance(senderId, senderBalance);
                    break;
                case "CREDITCARD":
                    Optional<CreditCard> optionalCreditCardSender = creditCardRepository.findByAccountHolder(optionalAccountHolder.get());
                    senderBalance = optionalCreditCardSender.get().getBalance().subtract(amount);
                    senderAccountType = AccountType.CREDITCARD;
                    senderId = optionalCreditCardSender.get().getId();
                    // Update balance of sender
                    creditCardService.updateBalance(senderId, senderBalance);
                    break;
                case "SAVINGS":
                    Optional<Savings> optionalSavingsSender = savingsRepository.findByAccountHolder(optionalAccountHolder.get());
                    senderBalance = optionalSavingsSender.get().getBalance().subtract(amount);
                    senderAccountType = AccountType.SAVINGS;
                    senderId = optionalSavingsSender.get().getId();
                    // Update balance of sender
                    savingsService.updateBalance(senderId, senderBalance);
                    break;
            }
        }


        // Get recipient account by verifying all possibilities and its new balance
        BigDecimal recipientBalance;

        // Case if it's a checking account
        if(checkingRepository.findByIdAndPrimaryOwner(accountId, primaryOwner).isPresent() ||
        checkingRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner).isPresent()) {
            // Assign the checking optional to a variable
            Optional<Checking> checkingOptionalRecipient = null;
            checkingOptionalRecipient = checkingRepository.findByIdAndPrimaryOwner(accountId, primaryOwner);
            if(!checkingOptionalRecipient.isPresent()) {
                checkingOptionalRecipient = checkingRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner);
            }
            // Calculate new balance of the recipient and update it
            recipientBalance = checkingOptionalRecipient.get().getBalance().add(amount);
            checkingService.updateBalance(checkingOptionalRecipient.get().getId(), recipientBalance);
            // Add the transaction
            Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, senderId,
                    AccountType.CHECKING, checkingOptionalRecipient.get().getId(), valueAsMoney, LocalDate.now());
            transactionRepository.save(transaction);
        }

        // Case if it's a credit card account
        else if(creditCardRepository.findByIdAndPrimaryOwner(accountId, primaryOwner).isPresent() ||
                creditCardRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner).isPresent()) {
            // Assign the credit card optional to a variable
            Optional<CreditCard> creditCardOptionalRecipient = null;
            creditCardOptionalRecipient = creditCardRepository.findByIdAndPrimaryOwner(accountId, primaryOwner);
            if(!creditCardOptionalRecipient.isPresent()) {
                creditCardOptionalRecipient = creditCardRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner);
            }
            // Calculate new balance of the recipient and update it
            recipientBalance = creditCardOptionalRecipient.get().getBalance().add(amount);
            creditCardService.updateBalance(creditCardOptionalRecipient.get().getId(), recipientBalance);
            // Add the transaction
            Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, senderId,
                    AccountType.CREDITCARD, creditCardOptionalRecipient.get().getId(), valueAsMoney, LocalDate.now());
            transactionRepository.save(transaction);
        }

        // Case if it's a savings account
        else if(savingsRepository.findByIdAndPrimaryOwner(accountId, primaryOwner).isPresent() ||
                savingsRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner).isPresent()) {
            // Assign the savings optional to a variable
            Optional<Savings> savingsOptionalRecipient = null;
            savingsOptionalRecipient = savingsRepository.findByIdAndPrimaryOwner(accountId, primaryOwner);
            if(!savingsOptionalRecipient.isPresent()) {
                savingsOptionalRecipient = savingsRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner);
            }
            // Calculate new balance of the recipient and update it
            recipientBalance = savingsOptionalRecipient.get().getBalance().add(amount);
            savingsService.updateBalance(savingsOptionalRecipient.get().getId(), recipientBalance);
            // Add the transaction
            Transaction transaction = new Transaction(TransactionType.MONEY_TRANSFER, senderAccountType, senderId,
                    AccountType.SAVINGS, savingsOptionalRecipient.get().getId(), valueAsMoney, LocalDate.now());
            transactionRepository.save(transaction);
        }
    }

    public boolean recipientExists(String primaryOwnerString, String secondaryOwnerString, Long accountId) {
        Owner primaryOwner = new Owner(primaryOwnerString);
        Owner secondaryOwner = new Owner(secondaryOwnerString);
        boolean result = false;
        if(checkingRepository.findByIdAndPrimaryOwner(accountId, primaryOwner).isPresent() ||
                checkingRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner).isPresent() ||
                creditCardRepository.findByIdAndPrimaryOwner(accountId, primaryOwner).isPresent() ||
                creditCardRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner).isPresent() ||
                savingsRepository.findByIdAndPrimaryOwner(accountId, primaryOwner).isPresent() ||
                savingsRepository.findByIdAndSecondaryOwner(accountId, secondaryOwner).isPresent()
        ) result = true;
        return result;
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
