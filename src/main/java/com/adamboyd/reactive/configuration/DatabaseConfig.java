package com.adamboyd.reactive.configuration;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/** Configuration of Reactive Database (Postgres) and version history (Flyway) **/
@Configuration
@PropertySource("classpath:/src/main/resources/application.yml")
@EnableR2dbcRepositories
@EnableConfigurationProperties({R2dbcProperties.class, FlywayProperties.class})
class DatabaseConfig extends AbstractR2dbcConfiguration {
    Environment environment;

    /** Configuration of version history (Flyway)
     * @param flywayProperties from spring.flyway
     * @param r2dbcProperties from spring.r2dbc
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

    /** Configuration of Database (Postgres) **/
   @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .username(environment.getProperty("r2dbc.username"))
                .password(environment.getProperty("r2dbc.password"))
                .build());
    }
}
