package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Savings;

import java.math.BigDecimal;

public interface ISavingsService {
//    Savings store(Savings savings);

    void addInterest(Long id);
}
