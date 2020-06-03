package com.distance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author 87796
 */
@SpringBootApplication
public class DistanceApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(DistanceApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DistanceApplication.class);
    }
}
