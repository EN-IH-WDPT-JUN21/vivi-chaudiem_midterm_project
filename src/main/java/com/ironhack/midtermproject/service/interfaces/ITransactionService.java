package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.enums.AccountType;

public interface ITransactionService {
    void transferMoney(String accountType, String value, String owner, Long accountId);
    void transferMoneyThirdParty(String hashedKey, String value, Long accountId, String secretKey);
}
