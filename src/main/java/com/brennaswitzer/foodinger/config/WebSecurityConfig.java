package com.brennaswitzer.foodinger.config;

import com.brennaswitzer.foodinger.security.UpdateDBAuthSuccessHandler;
import com.brennaswitzer.foodinger.util.CompoundAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    ClientRegistrationRepository oauthClientRepo;

    @Autowired
    UpdateDBAuthSuccessHandler updateDBAuthSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers(
                        "/",
                        "/login-providers",
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
            .logout(l -> l
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        if (oauthClientRepo != null) {
            http
                .oauth2Login(o -> o
                    .successHandler(new CompoundAuthenticationSuccessHandler(List.of(
                            updateDBAuthSuccessHandler,
                            new SavedRequestAwareAuthenticationSuccessHandler()
                    )))
                );
        }
        // @formatter:on
    }

    @Bean
    protected HttpSessionCsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

}
