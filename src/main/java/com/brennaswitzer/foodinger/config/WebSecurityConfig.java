package com.brennaswitzer.foodinger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers(
                        "/",
                        "/home",
                        "/webjars/**",
                        "/static/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .csrf(c -> c
                .csrfTokenRepository(csrfTokenRepository())
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login(o -> {})
            .logout(l -> l
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        // @formatter:on
    }

    @Bean
    protected HttpSessionCsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

}
