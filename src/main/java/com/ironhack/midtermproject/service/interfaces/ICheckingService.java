package com.ironhack.midtermproject.service.interfaces;


import com.ironhack.midtermproject.dao.AccountData.Checking;

import java.math.BigDecimal;

public interface ICheckingService {
    Checking store(Checking checking);
    void updateBalance(Long id, BigDecimal balance);
}
