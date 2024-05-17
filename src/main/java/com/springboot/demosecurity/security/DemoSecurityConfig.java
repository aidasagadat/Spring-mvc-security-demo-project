package com.springboot.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // add support for JDBC (no more hardcoded users)

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource); // tell spring security to use JDBC authentication with our data source
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/ceos/**").hasRole("CEO")
                        .anyRequest().authenticated()
                )
                .formLogin(form ->

                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()

                        )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"))
        ;





        return http.build();

    }
}








    /*

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
     */



















