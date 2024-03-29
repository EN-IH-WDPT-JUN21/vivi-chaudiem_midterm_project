package com.ironhack.midtermproject.dao.AccountData;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Account {

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE, optional = false)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    @DecimalMax(value = "100000", message = "The credit limit cannot be higher than 100.000")
    private BigDecimal creditLimit = BigDecimal.valueOf(100);

    @DecimalMin(value = "0.1", message = "The interest rate cannot be less than 0.1")
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);

    public CreditCard(BigDecimal balance, Owner primaryOwner, Owner secondaryOwner, LocalDateTime creationDate, AccountHolder accountHolder, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.accountHolder = accountHolder;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }
}
