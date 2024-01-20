package com.example.dbsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DbsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbsServiceApplication.class, args);
    }

}
