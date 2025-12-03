package com.surest.member.integration;

import com.surest.member.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer>{

    private static final String IMAGE_VERSION = "postgres:15";
    private static PostgresTestContainer container;



    private PostgresTestContainer() {
        super(IMAGE_VERSION);
        this.withDatabaseName("testdb");
        this.withUsername("testuser");
        this.withPassword("testpass");
        this.withReuse(false);
        this.addEnv("TZ", "Asia/Kolkata");
    }

    public static PostgresTestContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer()
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", this.getJdbcUrl());
        System.setProperty("DB_USERNAME", this.getUsername());
        System.setProperty("DB_PASSWORD", this.getPassword());
    }

    @Override
    public void stop() {
        // do nothing JVM handles container shutdown
    }
}

