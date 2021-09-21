package com.ironhack.midtermproject.dao.AccountData;

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
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    @NotBlank(message = "The secret key cannot be empty or null.")
    private String secretKey;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    // Can be instantiated with less, but not less than 100
    @DecimalMin(value = "100", message = "The minimum balance for instantiation is 100.")
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000);

    // Default 0,0025
    // Max. 0,5
    @DecimalMax(value = "0.5", message = "The interest rate cannot be higher than 0.5")
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    // How to add: per Year, check if exists 1 year or when it got interest the last time 1 year ago

}
