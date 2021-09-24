package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;

public interface ICreditCardController {
    CreditCard getCreditCardByIdAndPrimaryOwner(long id, String primaryOwner);
    CreditCard getCreditCardByIdAndSecondaryOwner(long id, String secondaryOwner);
}
