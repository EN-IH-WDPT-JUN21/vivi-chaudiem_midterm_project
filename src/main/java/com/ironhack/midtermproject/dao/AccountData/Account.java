package com.ironhack.midtermproject.dao.AccountData;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
