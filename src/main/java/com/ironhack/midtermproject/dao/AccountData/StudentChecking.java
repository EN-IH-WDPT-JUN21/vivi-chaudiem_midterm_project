package com.ironhack.midtermproject.dao.AccountData;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretKey;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    // Account creation only through checking Account
}
