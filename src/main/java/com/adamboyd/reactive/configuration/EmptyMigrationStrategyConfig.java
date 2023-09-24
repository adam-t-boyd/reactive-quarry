package com.adamboyd.reactive.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmptyMigrationStrategyConfig {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return Flyway::repair;
    }
}
