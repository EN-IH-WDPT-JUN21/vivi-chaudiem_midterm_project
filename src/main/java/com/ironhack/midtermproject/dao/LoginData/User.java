package com.ironhack.midtermproject.dao.LoginData;

import com.ironhack.midtermproject.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Role role = new Role(RoleName.ACCOUNT_HOLDER);
}
