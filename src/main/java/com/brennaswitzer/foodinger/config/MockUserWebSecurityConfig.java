package com.brennaswitzer.foodinger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@Profile("mock-user")
@Order(1)
public class MockUserWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .antMatcher("/mock-user/**")
            .authorizeRequests(a -> a
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable);
        // @formatter:on
    }

}
