package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SavingsController {

    @Autowired
    private SavingsRepository savingsRepository;

//    @Autowired
//    private ISavingsService savingsService;

    @PostMapping("/create/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings store(@RequestBody @Valid Savings savings) {
        return savingsRepository.save(savings);
    }

}
