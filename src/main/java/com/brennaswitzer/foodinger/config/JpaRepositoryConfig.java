package com.brennaswitzer.foodinger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.brennaswitzer.foodinger.data"},
        enableDefaultTransactions = false
)
public class JpaRepositoryConfig {
}
