package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;

import java.util.Optional;

public interface ICreditCardController {
    Optional<CreditCard> getCreditCardByIdAndPrimaryOwner(long id, String primaryOwner);
    Optional<CreditCard> getCreditCardByIdAndSecondaryOwner(long id, String secondaryOwner);
}
