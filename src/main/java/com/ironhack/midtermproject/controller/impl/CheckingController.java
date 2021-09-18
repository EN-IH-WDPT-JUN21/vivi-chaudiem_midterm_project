package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.repository.AccountDataRepositories.StudentCheckingRepository;
import com.ironhack.midtermproject.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CheckingController {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private ICheckingService checkingService;

    @PostMapping("/create/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public <T extends Account> T store(@RequestBody @Valid T checking) {
        return checkingService.store(checking);
    }

}
