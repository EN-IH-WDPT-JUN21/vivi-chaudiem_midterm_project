package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardRepository creditCardRepository;
//
//    @Autowired
//    private ICreditCardService creditCardService;

    @GetMapping("/creditcard")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @GetMapping("/creditcard/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard findById(@PathVariable Long id) {
        return creditCardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card account not found."));
    }

    @PostMapping("/create/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard store(@RequestBody @Valid CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

}
