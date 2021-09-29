package com.ironhack.midtermproject.dao.AccountData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Savings extends Account {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    @NotBlank(message = "The secret key cannot be empty or null.")
    private String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @DecimalMin(value = "100", message = "The minimum balance for instantiation is 100.")
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000);

    @DecimalMax(value = "0.5", message = "The interest rate cannot be higher than 0.5")
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    public Savings(BigDecimal balance, Owner primaryOwner, Owner secondaryOwner, LocalDateTime creationDate, AccountHolder accountHolder, String secretKey, Status status, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.accountHolder = accountHolder;
        this.secretKey = secretKey;
        this.status = status;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }
}
