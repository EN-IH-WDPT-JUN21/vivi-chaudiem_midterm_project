package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;

public interface ICreditCardService {

    void addInterest(Long id);
    void update(Long id, CreditCard creditCard);
}
