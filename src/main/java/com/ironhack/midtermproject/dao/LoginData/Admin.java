package com.ironhack.midtermproject.dao.LoginData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {

    private String name;

    public Admin(String username, String password, Role role, String name) {
        super(username, password, role);
        this.name = name;
    }
}
