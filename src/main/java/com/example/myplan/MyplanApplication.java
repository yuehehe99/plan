package com.example.myplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyplanApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyplanApplication.class, args);
    }
}
