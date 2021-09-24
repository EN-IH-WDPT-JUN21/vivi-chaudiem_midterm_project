package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interfaces.ICheckingController;
import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.repository.AccountDataRepositories.CheckingRepository;
import com.ironhack.midtermproject.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CheckingController implements ICheckingController {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private ICheckingService checkingService;

    @GetMapping("/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAll() {
        return checkingRepository.findAll();
    }

    @GetMapping("/checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Checking findById(@PathVariable Long id) {
        return checkingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found."));
    }

    @PostMapping("/create/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking store(@RequestBody @Valid Checking checking) {
        return checkingService.store(checking);
    }

    @PutMapping("/modify/checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(name = "id") Long id, @RequestBody @Valid Checking checking) {
        checkingService.update(id, checking);
    }

    @GetMapping(value = "/checking", params = {"id", "primaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public Checking getCheckingByIdAndPrimaryOwner(@RequestParam long id, @RequestParam String primaryOwner) {
        Owner owner = new Owner(primaryOwner);
        return checkingRepository.findByIdAndPrimaryOwner(id, owner);
    }

    @GetMapping(value = "/checking", params = {"id", "secondaryOwner"})
    @ResponseStatus(HttpStatus.OK)
    public Checking getCheckingByIdAndSecondaryOwner(@RequestParam long id, @RequestParam String secondaryOwner) {
        Owner owner = new Owner(secondaryOwner);
        return checkingRepository.findByIdAndSecondaryOwner(id, owner);
    }
}
