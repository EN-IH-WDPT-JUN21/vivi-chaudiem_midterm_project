package com.ironhack.midtermproject.dao.LoginData;

import com.ironhack.midtermproject.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // String or enum?
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Role(RoleName name) {
        this.name = name;
    }
}
