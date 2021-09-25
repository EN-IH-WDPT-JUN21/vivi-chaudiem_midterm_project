package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.repository.TransactionRepository;
import com.ironhack.midtermproject.service.interfaces.ITransactionService;
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

    @Autowired
    private ITransactionService transactionService;

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

    @PutMapping("/transfer/{accountType}/{value}/{owner}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void transferMoney(@PathVariable String accountType, @PathVariable String value, @PathVariable String owner,
                              @PathVariable(value = "id") Long accountId) {
        transactionService.transferMoney(accountType, value, owner, accountId);
    }

    /*
        String accountType, String value, String primaryOwnerString, String secondaryOwnerString, Long accountId) {

     */

//    @PutMapping("/transfer/{value}/{sender}/{senderId}/{recipient}/{recipientId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void transferMoney(@PathVariable String value, @PathVariable(value = "sender") String senderAccountType, @PathVariable(value = "senderId") Long senderAccountId,
//                              @PathVariable(value = "recipient") String recipientAccountType, @PathVariable(value = "recipientId") Long recipientAccountId) {
//        transactionService.transferMoney(value, senderAccountType, senderAccountId, recipientAccountType, recipientAccountId);
//    }


}
