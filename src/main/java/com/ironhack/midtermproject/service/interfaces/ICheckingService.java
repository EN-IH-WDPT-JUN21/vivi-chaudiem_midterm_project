package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.controller.dto.CheckingDTO;
import com.ironhack.midtermproject.dao.AccountData.Checking;

public interface ICheckingService {
    Checking store(CheckingDTO checking);
}
