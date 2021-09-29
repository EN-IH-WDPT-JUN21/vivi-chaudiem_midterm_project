package com.ironhack.midtermproject.security;

import com.ironhack.midtermproject.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/accountholder/{id}").hasAnyRole("ACCOUNT_HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/savings/{id}").hasAnyRole("ACCOUNT_HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/creditcard/{id}").hasAnyRole("ACCOUNT_HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/checking/{id}").hasAnyRole("ACCOUNT_HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/third_party/{id}").hasAnyRole("THIRD_PARTY", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/transactions/{id}").hasAnyRole("ACCOUNT_HOLDER", "THIRD_PARTY", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/transactions/savings/{id}").hasAnyRole("ACCOUNT_HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.PUT, "/transfer/third_party").hasRole("THIRD_PARTY")
                .mvcMatchers(HttpMethod.PUT, "/transfer/{accountType}/{value}/{owner}/{id}").hasRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.GET, "/savings").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/third_party").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/accountholder").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/creditcard").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/checking").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/transactions").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/create/accountholder").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/create/savings").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/create/creditcard").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/create/checking").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/create/third_party").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/modify/savings/{id}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/modify/creditcard/{id}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/modify/checking/{id}").hasRole("ADMIN")
                .anyRequest().permitAll();
    }
}
