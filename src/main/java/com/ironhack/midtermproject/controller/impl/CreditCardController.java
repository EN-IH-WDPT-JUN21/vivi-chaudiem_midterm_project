package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interfaces.ICreditCardController;
import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CreditCardController implements ICreditCardController {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ICreditCardService creditCardService;

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

    @PatchMapping("/creditcard/interest/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInterest(@PathVariable(value = "id") Long id) {
        creditCardService.addInterest(id);
    }

    @PutMapping("/modify/creditcard/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(name = "id") Long id, @RequestBody @Valid CreditCard creditCard) {
        creditCardService.update(id, creditCard);
    }

    @GetMapping(value = "/creditcard", params = {"id", "primaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getCreditCardByIdAndPrimaryOwner(@RequestParam long id, @RequestParam String primaryOwner) {
        Owner owner = new Owner(primaryOwner);
        return creditCardRepository.findByIdAndPrimaryOwner(id, owner);
    }

    @GetMapping(value = "/creditcard", params = {"id", "secondaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getCreditCardByIdAndSecondaryOwner(@RequestParam long id, @RequestParam String secondaryOwner) {
        Owner owner = new Owner(secondaryOwner);
        return creditCardRepository.findByIdAndSecondaryOwner(id, owner);
    }

}
