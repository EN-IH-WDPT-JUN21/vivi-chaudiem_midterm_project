package com.ironhack.midtermproject.service.interfaces;


import com.ironhack.midtermproject.dao.AccountData.Checking;

public interface ICheckingService {
    Checking store(Checking checking);
    void update(Long id, Checking checking);
}
