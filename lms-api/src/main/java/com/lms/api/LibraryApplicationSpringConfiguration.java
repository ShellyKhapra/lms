package com.lms.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {
                "com.lms.mappers",
                "com.lms.dal",
                "com.lms.service",
                "com.lms.api"
        })
public class LibraryApplicationSpringConfiguration {
}
