package com.ironhack.midtermproject.dao.AccountData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Default 100
    // Not higher than 100.000
    @DecimalMax(value = "100000", message = "The credit limit cannot be higher than 100.000")
    private BigDecimal creditLimit = BigDecimal.valueOf(100);

    // Default 0,2
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);

    // Can be instantiated with less, but not less than 0,1

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(BigDecimal.valueOf(0.1)) == -1) {
            System.out.println("The interest rate cannot be lower than 0.1");
        } else {
            this.interestRate = interestRate;
        }
    }

    // How to add: Per month, check if 1 month created / got interest over 1 month ago
}
