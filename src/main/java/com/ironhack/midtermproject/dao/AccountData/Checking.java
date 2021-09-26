package com.ironhack.midtermproject.dao.AccountData;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import com.ironhack.midtermproject.enums.CheckingType;
import com.ironhack.midtermproject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Checking extends Account {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE, optional = false)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    private String secretKey;

    @Enumerated(EnumType.STRING)
    private CheckingType checkingType = CheckingType.STANDARD_CHECKING;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    // 250
    private BigDecimal minimumBalance = BigDecimal.valueOf(250);

    // 12
    private BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);

    // Check if AccountHolder is < 24 ? creation StudentCheckingAccount : normal Checking Account
}
