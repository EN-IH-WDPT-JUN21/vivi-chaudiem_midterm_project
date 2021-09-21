package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SavingsController {

    @Autowired
    private SavingsRepository savingsRepository;

//    @Autowired
//    private ISavingsService savingsService;

    @GetMapping("/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> findAll() {
        return savingsRepository.findAll();
    }

    @GetMapping("/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Savings findById(@PathVariable Long id) {
        return savingsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found."));
    }

    @PostMapping("/create/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings store(@RequestBody @Valid Savings savings) {
        return savingsRepository.save(savings);
    }

}
