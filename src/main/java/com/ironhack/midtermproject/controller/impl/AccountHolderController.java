package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.repository.LoginDataRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountHolderController {
    @Autowired
    private AccountHolderRepository accountHolderRepository;


    @GetMapping("/accountholder")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAll() {
        return accountHolderRepository.findAll();
    }

    @GetMapping("/accountholder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder findById(@PathVariable Long id) {
        return accountHolderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found."));
    }


    @PostMapping("/create/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder store(@RequestBody @Valid AccountHolder accountHolder) {
        return accountHolderRepository.save(accountHolder);
    }

}
