package com.ironhack.midtermproject.service.interfaces;

import com.ironhack.midtermproject.dao.Money;
import com.ironhack.midtermproject.enums.AccountType;

public interface ITransactionService {
    void transferMoney(String value, String senderAccountType, Long senderAccountId, String recipientAccountType, Long recipientAccountId);
}
