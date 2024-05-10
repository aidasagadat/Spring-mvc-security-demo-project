package com.springboot.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails adiya = User.builder()
                .username("adiya")
                .password("{noop}test")
                .roles("EMPLOYEE")
                .build();


        UserDetails dilnaz = User.builder()
                .username("dilnaz")
                .password("{noop}test")
                .roles("EMPLOYEE", "MANAGER")
                .build();


        UserDetails aida = User.builder()
                .username("aida")
                .password("{noop}test")
                .roles("EMPLOYEE", "MANAGER", "CEO")
                .build();

        return new InMemoryUserDetailsManager(adiya, dilnaz, aida);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                        .anyRequest().authenticated()
                )
                .formLogin(form ->

                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()

                        );

        return http.build();

    }
}


























