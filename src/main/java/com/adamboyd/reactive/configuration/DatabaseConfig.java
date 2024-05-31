package com.adamboyd.reactive.configuration;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.Objects;

/** Configuration of Reactive Database (Postgres) and version history (Flyway) **/
@Configuration
@EnableR2dbcRepositories
@EnableConfigurationProperties({R2dbcProperties.class, FlywayProperties.class})
class DatabaseConfig extends AbstractR2dbcConfiguration {
    private Environment environment;

    /** Configuration of version history (Flyway)
     * @param flywayProperties from spring.flyway
     * @param r2dbcProperties from spring.r2dbc
     * @return Flyway bean
     * **/
    @Bean(initMethod = "migrate")
    public Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
        return Flyway.configure()
                .dataSource(
                        flywayProperties.getUrl(),
                        r2dbcProperties.getUsername(),
                        r2dbcProperties.getPassword()
                )
                .locations(flywayProperties.getLocations()
                        .stream()
                        .toArray(String[]::new))
                .baselineOnMigrate(true)
                .load();
    }

    /** Configuration of Database (Postgres)
     * @return Postgres Connection Factory Bean **/
   @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .username(Objects.requireNonNull(environment.getProperty("r2dbc.username")))
                .password(Objects.requireNonNull(environment.getProperty("r2dbc.password")))
                .build());
    }
}
