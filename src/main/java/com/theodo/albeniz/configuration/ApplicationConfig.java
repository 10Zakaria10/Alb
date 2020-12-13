package com.theodo.albeniz.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationConfig {


    @Data
    public class  ApiConfiguration{
        public int maxCollection;
        public boolean ascending;


    }
    public  ApiConfiguration api = new ApiConfiguration();

}
