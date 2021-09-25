package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.dto.BalanceDTO;
import com.ironhack.midtermproject.controller.interfaces.ISavingsController;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.repository.AccountDataRepositories.SavingsRepository;
import com.ironhack.midtermproject.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class SavingsController implements ISavingsController {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private ISavingsService savingsService;

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

    @PatchMapping("/savings/interest/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInterest(@PathVariable(value = "id") Long id) {
        savingsService.addInterest(id);
    }

    @PatchMapping("/modify/savings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") Long id, @RequestBody @Valid BalanceDTO balanceDTO) {
        savingsService.updateBalance(id, balanceDTO.getBalance());
    }

    @GetMapping(value = "/savings", params = {"id", "primaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Savings> getSavingsByIdAndPrimaryOwner(@RequestParam long id, @RequestParam String primaryOwner) {
        Owner owner = new Owner(primaryOwner);
        return savingsRepository.findByIdAndPrimaryOwner(id, owner);
    }

    @GetMapping(value = "/savings", params = {"id", "secondaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Savings> getSavingsByIdAndSecondaryOwner(@RequestParam long id, @RequestParam String secondaryOwner) {
        Owner owner = new Owner(secondaryOwner);
        return savingsRepository.findByIdAndSecondaryOwner(id, owner);
    }
}
