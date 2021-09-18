package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interfaces.IAccountController;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    // GetMapping
    // Get access to the account: AccountHolders and Admins

    // PutMapping
    // Send Money to another Account: AccountHolders, ThirdPartyAccount

    // PutMapping
    // Get Money from another Account: AccountHolders, ThirdPartyAccount

    // PutMapping
    // Withdraw Money from Account: AccountHolders

    // PutMapping
    // Deposit Money to Account: AccountHolders

    // PostMapping
    // Create new Accounts (Checking, Savings, CreditCard): Admins
    // @PostMapping("/create/checking")

    // PutMapping
    // Modify data of the account: Admins

    // PostMapping
    // ThirdParty Accounts?

    // PutMapping
    // Add InterestRate
}
