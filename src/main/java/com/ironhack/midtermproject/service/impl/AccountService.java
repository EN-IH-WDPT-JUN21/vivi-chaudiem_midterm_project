package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.dao.LoginData.User;
import com.ironhack.midtermproject.repository.AccountDataRepositories.*;
import com.ironhack.midtermproject.repository.LoginDataRepositories.UserRepository;
import com.ironhack.midtermproject.security.CustomUserDetails;
import com.ironhack.midtermproject.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountService implements IAccountService {

//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private CheckingRepository checkingRepository;
//
//    @Autowired
//    private StudentCheckingRepository studentCheckingRepository;
//
//    @Autowired
//    private CreditCardRepository creditCardRepository;
//
//    @Autowired
//    private SavingsRepository savingsRepository;


}
