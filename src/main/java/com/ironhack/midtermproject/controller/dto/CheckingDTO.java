package com.ironhack.midtermproject.controller.dto;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.enums.CheckingType;
import com.ironhack.midtermproject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckingDTO {
    private Long id;
    private String secretKey;
    private Owner primaryOwner;
    private Owner secondaryOwner;
    private BigDecimal balance;
    private BigDecimal penaltyFee;
    private LocalDate creationDate;
    private CheckingType checkingType;
    private Status status;
    private AccountHolder accountHolder;
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;

}
