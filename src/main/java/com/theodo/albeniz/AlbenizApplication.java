package com.theodo.albeniz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.theodo.albeniz.configuration")
public class AlbenizApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlbenizApplication.class, args);
    }

}
