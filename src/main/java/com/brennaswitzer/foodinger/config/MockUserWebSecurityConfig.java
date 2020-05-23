package com.brennaswitzer.foodinger.config;

import com.brennaswitzer.foodinger.security.MockUserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@Profile("mock-user")
@Order(1)
public class MockUserWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MockUserAuthenticationProvider mockUserAuthenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(mockUserAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .antMatcher("/mock-user/**")
            .authorizeRequests(a -> a
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable);
        http.formLogin(f -> f
            .loginProcessingUrl("/mock-user/login")
        );
        // @formatter:on
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
