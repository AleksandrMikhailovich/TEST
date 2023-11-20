package com.example.cameratest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CameraTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CameraTestApplication.class, args);
    }

}
