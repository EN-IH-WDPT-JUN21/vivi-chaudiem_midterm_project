package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Checking;

import java.util.Optional;

public interface ICheckingController {
    Optional<Checking> getCheckingByIdAndPrimaryOwner(long id, String primaryOwner);
    Optional<Checking> getCheckingByIdAndSecondaryOwner(long id, String secondaryOwner);
}
