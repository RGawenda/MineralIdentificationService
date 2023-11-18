package com.mineralidentificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
@EntityScan(basePackages = "com.mineralidentificationservice.model")

public class MineralIdentificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MineralIdentificationServiceApplication.class, args);
    }
}
