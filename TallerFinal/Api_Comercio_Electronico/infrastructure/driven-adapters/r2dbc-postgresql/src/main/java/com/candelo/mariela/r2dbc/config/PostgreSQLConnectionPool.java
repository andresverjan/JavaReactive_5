package com.candelo.mariela.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class PostgreSQLConnectionPool {
    // TODO: change pool connection properties based on your resources.
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;

    @Bean
    public ConnectionPool connectionPool() {

        PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .port(5432)
                        .database("postgres")
                        .schema("ecommerce")
                        .username("postgres")
                        .password("2906")
                        .build());

        return new ConnectionPool(
                ConnectionPoolConfiguration.builder()
                        .connectionFactory(connectionFactory)
                        .initialSize(INITIAL_SIZE)
                        .maxSize(MAX_SIZE)
                        .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                        .validationQuery("SELECT 1")
                        .build());
    }


}