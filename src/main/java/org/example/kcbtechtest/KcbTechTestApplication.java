package org.example.kcbtechtest;

import org.example.kcbtechtest.config.JwtConverterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {JwtConverterProperties.class})
public class KcbTechTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(KcbTechTestApplication.class, args);
    }

}
