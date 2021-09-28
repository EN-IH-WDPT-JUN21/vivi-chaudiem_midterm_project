package com.ironhack.midtermproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("com.ironhack.midtermproject.dao.AccountData.Account")
//@ComponentScan(basePackages = {"com.ironhack.midtermproject.dao.AccountData.Account"})
//@EntityScan("com.ironhack.midtermproject.dao.AccountData.Account")
public class MidtermProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidtermProjectApplication.class, args);
	}

}
