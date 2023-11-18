package com.mineralidentificationservice.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
public class DatabaseConfiguration {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String maxActive;
}
