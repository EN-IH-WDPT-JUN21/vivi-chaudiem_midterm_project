package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interfaces.IAccountController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements IAccountController {

//    @Autowired
//    private CheckingRepository checkingRepository;
//
//    @Autowired
//    private CreditCardRepository creditCardRepository;
//
//    @Autowired
//    private SavingsRepository savingsRepository;

    // TODO:
    // Authentication & Co

    // PutMapping
    // Send Money to another Account: AccountHolders, ThirdPartyAccount

    // PutMapping
    // Get Money from another Account: AccountHolders, ThirdPartyAccount

    // PutMapping
    // Withdraw Money from Account: AccountHolders

    // PutMapping
    // Deposit Money to Account: AccountHolders

    // PostMapping
    // ThirdParty Accounts?


    // ***Done***

    // GetMapping
    // Get access to the account: AccountHolders and Admins

    // PutMapping
    // Add InterestRate

    // PostMapping
    // Create new Accounts (Checking, Savings, CreditCard): Admins
    // @PostMapping("/create/checking")

    // PutMapping
    // Modify data of the account: Admins
}
