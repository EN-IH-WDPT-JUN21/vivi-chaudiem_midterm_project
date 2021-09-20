package com.ironhack.midtermproject.dao.AccountData;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance = BigDecimal.valueOf(0);
    private final BigDecimal penaltyFee = BigDecimal.valueOf(40);
    // Will be deducted automatically if balance < minimumBalance

    @NotBlank(message = "The primary owner cannot be empty or null.")
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

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_HOLDER_ID", referencedColumnName = "id")
    private AccountHolder accountHolder;

    public Account(Owner primaryOwner, Owner secondaryOwner, AccountHolder accountHolder) {
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.accountHolder = accountHolder;
    }

    public Account(Owner primaryOwner, AccountHolder accountHolder) {
        this.primaryOwner = primaryOwner;
        this.accountHolder = accountHolder;
    }
}
