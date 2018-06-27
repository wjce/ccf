package com.wjc.ccf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.wjc.ccf")
public class ApplicationApi {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationApi.class,args);
    }
}