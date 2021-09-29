package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;

import java.math.BigDecimal;

public interface ICreditCardService {

    void addInterest(Long id);
    void updateBalance(Long id, BigDecimal balance);
    CreditCard store(CreditCard creditCard);
}
