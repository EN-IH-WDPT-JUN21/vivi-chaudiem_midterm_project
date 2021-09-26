package com.ironhack.midtermproject.dao.AccountData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal balance = BigDecimal.valueOf(0);
    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);
    // Will be deducted automatically if balance < minimumBalance

    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "primary_owner"))
    })
    @Embedded
    private Owner primaryOwner;

    //Optional: Secondary Owner
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "secondary_owner"))
    })
    @Embedded
    private Owner secondaryOwner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate = LocalDateTime.now();
}

/*
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private Money balance;
    private String secretKey;
    @ManyToOne
    @NotNull
    @Valid
    @JoinColumn(name = "account_holder")
    private AccountHolder accountHolder;
    @ManyToOne
    @Valid
    @JoinColumn(name = "secondary_account_holder")
    private AccountHolder secondaryAccountHolder;
    private LocalDateTime creationDate;

    ThirdPartyTransaction class
 */
