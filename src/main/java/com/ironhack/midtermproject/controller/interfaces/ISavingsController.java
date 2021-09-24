package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Savings;

import java.util.Optional;

public interface ISavingsController {
    Optional<Savings> getSavingsByIdAndPrimaryOwner(long id, String primaryOwner);
    Optional<Savings> getSavingsByIdAndSecondaryOwner(long id, String secondaryOwner);

}
