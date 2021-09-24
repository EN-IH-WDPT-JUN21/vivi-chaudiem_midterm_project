package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;

import java.util.List;
import java.util.Optional;

public interface ISavingsController {
    Savings getSavingsByIdAndPrimaryOwner(long id, String primaryOwner);
    Savings getSavingsByIdAndSecondaryOwner(long id, String secondaryOwner);

}
