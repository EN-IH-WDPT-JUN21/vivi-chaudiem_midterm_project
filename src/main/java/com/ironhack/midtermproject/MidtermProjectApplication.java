package com.ironhack.midtermproject;

import com.ironhack.midtermproject.dao.LoginData.Admin;
import com.ironhack.midtermproject.dao.LoginData.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.ironhack.midtermproject.dao.AccountData.Account")
//@ComponentScan(basePackages = {"com.ironhack.midtermproject.dao.AccountData.Account"})
//@EntityScan("com.ironhack.midtermproject.dao.AccountData.Account")
public class MidtermProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidtermProjectApplication.class, args);
	}

}
