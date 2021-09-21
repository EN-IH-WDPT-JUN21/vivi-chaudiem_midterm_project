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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Account {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    // Default 100
    // Not higher than 100.000
    @DecimalMax(value = "100000", message = "The credit limit cannot be higher than 100.000")
    private BigDecimal creditLimit = BigDecimal.valueOf(100);

    // Default 0,2
    // Can be instantiated with less, but not less than 0,1
    @DecimalMin(value = "0.1", message = "The interest rate cannot be less than 0.1")
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);


    // How to add: Per month, check if 1 month created / got interest over 1 month ago
}
