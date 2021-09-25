package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.*;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.User;
import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.CheckingType;
import com.ironhack.midtermproject.enums.TransactionType;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AccountHolderRepository;
import com.ironhack.midtermproject.repository.LoginDataRepositories.UserRepository;
import com.ironhack.midtermproject.repository.TransactionRepository;
import com.ironhack.midtermproject.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public void transferMoney(String accountType, String value, String ownerString, Long accountId) {
        // Transform input values
        Owner owner = new Owner(ownerString);
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

        if(recipientExists(ownerString, accountId)) {
            switch(accountType.toUpperCase()) {
                case "CHECKING":
                    Optional<Checking> optionalCheckingSender = checkingRepository.findByAccountHolder(optionalAccountHolder.get());
                    senderBalance = optionalCheckingSender.get().getBalance().subtract(amount);
                    senderAccountType = AccountType.CHECKING;
                    senderId = optionalCheckingSender.get().getId();
                    // Update balance of sender if enough money
                    if(hasEnoughMoney(amount, optionalCheckingSender.get().getBalance())) {
                        // Check if penalty must be deducted
                        if(optionalCheckingSender.get().getCheckingType() == CheckingType.STANDARD_CHECKING &&
                            belowMinimumBalance(senderBalance, optionalCheckingSender.get().getMinimumBalance())) {
                                senderBalance = senderBalance.subtract(optionalCheckingSender.get().getPenaltyFee());
                        }
                        savingsService.updateBalance(senderId, senderBalance);
                        checkingService.updateBalance(senderId, senderBalance);
                    } else {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough money!");
                    }
                    break;
                case "CREDITCARD":
                    Optional<CreditCard> optionalCreditCardSender = creditCardRepository.findByAccountHolder(optionalAccountHolder.get());
                    senderBalance = optionalCreditCardSender.get().getBalance().subtract(amount);
                    senderAccountType = AccountType.CREDITCARD;
                    senderId = optionalCreditCardSender.get().getId();
                    // Update balance of sender if enough money
                    if(hasEnoughMoney(amount, optionalCreditCardSender.get().getBalance())) {
                        creditCardService.updateBalance(senderId, senderBalance);
                    } else {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough money!");
                    }
                    break;
                case "SAVINGS":
                    Optional<Savings> optionalSavingsSender = savingsRepository.findByAccountHolder(optionalAccountHolder.get());
                    senderBalance = optionalSavingsSender.get().getBalance().subtract(amount);
                    senderAccountType = AccountType.SAVINGS;
                    senderId = optionalSavingsSender.get().getId();
                    // Update balance of sender if enough money
                    if(hasEnoughMoney(amount, optionalSavingsSender.get().getBalance())) {
                        // Check if penalty must be deducted
                        if(belowMinimumBalance(senderBalance, optionalSavingsSender.get().getMinimumBalance())) {
                            senderBalance = senderBalance.subtract(optionalSavingsSender.get().getPenaltyFee());
                        }
                        savingsService.updateBalance(senderId, senderBalance);
                    } else {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough money!");
                    }
                    break;
            }
        }


        // Get recipient account by verifying all possibilities and its new balance
        BigDecimal recipientBalance;

        // Case if it's a checking account
        if(checkingRepository.findByIdAndPrimaryOwner(accountId, owner).isPresent() ||
        checkingRepository.findByIdAndSecondaryOwner(accountId, owner).isPresent()) {
            // Assign the checking optional to a variable
            Optional<Checking> checkingOptionalRecipient = null;
            checkingOptionalRecipient = checkingRepository.findByIdAndPrimaryOwner(accountId, owner);
            if(!checkingOptionalRecipient.isPresent()) {
                checkingOptionalRecipient = checkingRepository.findByIdAndSecondaryOwner(accountId, owner);
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
        else if(creditCardRepository.findByIdAndPrimaryOwner(accountId, owner).isPresent() ||
                creditCardRepository.findByIdAndSecondaryOwner(accountId, owner).isPresent()) {
            // Assign the credit card optional to a variable
            Optional<CreditCard> creditCardOptionalRecipient = null;
            creditCardOptionalRecipient = creditCardRepository.findByIdAndPrimaryOwner(accountId, owner);
            if(!creditCardOptionalRecipient.isPresent()) {
                creditCardOptionalRecipient = creditCardRepository.findByIdAndSecondaryOwner(accountId, owner);
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
        else if(savingsRepository.findByIdAndPrimaryOwner(accountId, owner).isPresent() ||
                savingsRepository.findByIdAndSecondaryOwner(accountId, owner).isPresent()) {
            // Assign the savings optional to a variable
            Optional<Savings> savingsOptionalRecipient = null;
            savingsOptionalRecipient = savingsRepository.findByIdAndPrimaryOwner(accountId, owner);
            if(!savingsOptionalRecipient.isPresent()) {
                savingsOptionalRecipient = savingsRepository.findByIdAndSecondaryOwner(accountId, owner);
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

    public boolean recipientExists(String ownerString, Long accountId) {
        Owner owner = new Owner(ownerString);
        boolean result = false;
        if(checkingRepository.findByIdAndPrimaryOwner(accountId, owner).isPresent() ||
                checkingRepository.findByIdAndSecondaryOwner(accountId, owner).isPresent() ||
                creditCardRepository.findByIdAndPrimaryOwner(accountId, owner).isPresent() ||
                creditCardRepository.findByIdAndSecondaryOwner(accountId, owner).isPresent() ||
                savingsRepository.findByIdAndPrimaryOwner(accountId, owner).isPresent() ||
                savingsRepository.findByIdAndSecondaryOwner(accountId, owner).isPresent()
        ) result = true;
        return result;
    }

    public boolean hasEnoughMoney(BigDecimal amount, BigDecimal currentBalance) {
        return amount.compareTo(currentBalance) > 0 ? false : true;
    }

    public boolean belowMinimumBalance(BigDecimal balance, BigDecimal minimumBalance) {
        return balance.compareTo(minimumBalance) > 0 ? false : true;
    }

}
