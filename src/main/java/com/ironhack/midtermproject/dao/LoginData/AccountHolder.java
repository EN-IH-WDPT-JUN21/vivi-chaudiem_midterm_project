package com.ironhack.midtermproject.dao.LoginData;

import com.ironhack.midtermproject.dao.AccountData.Account;
import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class AccountHolder extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate dateOfBirth;

    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "primary_street")),
            @AttributeOverride(name = "city", column = @Column(name = "primary_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_postal_code"))
    })
    @Embedded
    private Address primaryAddress;

    // Optional
    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code"))
    })
    @Embedded
    private Address mailingAddress;

    @OneToOne(mappedBy = "accountHolder")
    @JsonIgnore
    private Savings savings;

    @OneToOne(mappedBy = "accountHolder")
    @JsonIgnore
    private CreditCard creditCard;

    @OneToOne(mappedBy = "accountHolder")
    @JsonIgnore
    private Checking checking;
}
