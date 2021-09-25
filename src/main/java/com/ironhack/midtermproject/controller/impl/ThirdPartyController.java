package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.LoginData.ThirdParty;
import com.ironhack.midtermproject.repository.LoginDataRepositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ThirdPartyController {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @GetMapping("/third_party")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAll() {
        return thirdPartyRepository.findAll();
    }

    @GetMapping("/third_party/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty findById(@PathVariable Long id) {
        return thirdPartyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Third party not found."));
    }

    @PostMapping("/create/third_party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty store(@RequestBody @Valid ThirdParty thirdParty) {
        return thirdPartyRepository.save(thirdParty);
    }
}
