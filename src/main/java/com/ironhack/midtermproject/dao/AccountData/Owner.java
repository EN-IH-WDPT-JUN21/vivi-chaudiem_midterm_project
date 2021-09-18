package com.ironhack.midtermproject.dao.AccountData;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
public class Owner {
    private String name;
}
