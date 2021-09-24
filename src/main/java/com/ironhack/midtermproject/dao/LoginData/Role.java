package com.ironhack.midtermproject.dao.LoginData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // String or enum?
    private String name;

    @OneToOne(mappedBy = "role")
    @JsonIgnore
    private User user;

    public Role(String name) {
        this.name = name;
    }

    //    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}
