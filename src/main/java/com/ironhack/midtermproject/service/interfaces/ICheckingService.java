package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Account;

public interface ICheckingService {
    <T extends Account> T store(T checking);
}
