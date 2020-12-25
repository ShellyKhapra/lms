package com.lms.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {
                "com.lms.api",
                "com.lms.common.models",
                "com.lms.service"
        })
public class LibraryApplicationSpringConfiguration {
}
