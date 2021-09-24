package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Checking;

public interface ICheckingController {
    Checking getCheckingByIdAndPrimaryOwner(long id, String primaryOwner);
    Checking getCheckingByIdAndSecondaryOwner(long id, String secondaryOwner);
}
