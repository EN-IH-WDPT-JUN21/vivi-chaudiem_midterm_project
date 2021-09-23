package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction findById(@PathVariable Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found."));
    }

    @GetMapping("/transactions/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> findByAccountOneId(@PathVariable(value="id")  Long accountOneId) {
        return transactionRepository.findByAccountOneIdAndAccountOneType(accountOneId, AccountType.SAVINGS);
    }

}
