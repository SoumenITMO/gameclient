package com.gameclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GameclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameclientApplication.class, args);
    }

}
