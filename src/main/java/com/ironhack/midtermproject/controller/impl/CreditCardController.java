package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.dto.BalanceDTO;
import com.ironhack.midtermproject.controller.interfaces.ICreditCardController;
import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CreditCardRepository;
import com.ironhack.midtermproject.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        return creditCardService.store(creditCard);
    }

    @PatchMapping("/creditcard/interest/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInterest(@PathVariable(value = "id") Long id) {
        creditCardService.addInterest(id);
    }

    @PatchMapping("/modify/creditcard/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") Long id, @RequestBody @Valid BalanceDTO balanceDTO) {
        creditCardService.updateBalance(id, balanceDTO.getBalance());
    }

    @GetMapping(value = "/creditcard", params = {"id", "primaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<CreditCard> getCreditCardByIdAndPrimaryOwner(@RequestParam long id, @RequestParam String primaryOwner) {
        Owner owner = new Owner(primaryOwner);
        return creditCardRepository.findByIdAndPrimaryOwner(id, owner);
    }

    @GetMapping(value = "/creditcard", params = {"id", "secondaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<CreditCard> getCreditCardByIdAndSecondaryOwner(@RequestParam long id, @RequestParam String secondaryOwner) {
        Owner owner = new Owner(secondaryOwner);
        return creditCardRepository.findByIdAndSecondaryOwner(id, owner);
    }

}
