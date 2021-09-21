package com.ironhack.midtermproject.dao.AccountData;

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
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "account_id")
public class Checking extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretKey;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private CheckingType checkingType = CheckingType.NORMAL_CHECKING;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    // 250
    private final BigDecimal minimumBalance = BigDecimal.valueOf(250);

    // 12
    private final BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);

    // Check if AccountHolder is < 24 ? creation StudentCheckingAccount : normal Checking Account
}
