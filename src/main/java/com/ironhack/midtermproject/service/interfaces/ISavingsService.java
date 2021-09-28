package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.AccountData.Savings;

import java.math.BigDecimal;

public interface ISavingsService {
    void addInterest(Long id);
    void update(Long id, Savings savings);
    void updateBalance(Long id, BigDecimal balance);
    Savings store(Savings savings);
}
