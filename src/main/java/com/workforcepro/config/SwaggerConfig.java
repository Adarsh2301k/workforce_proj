package com.workforcepro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI workforceOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Workforce HRMS API")
                                .version("1.0")
                                .description(
                                        "Enterprise HRMS built with Spring Boot, JWT Authentication, Employee, Department, Leave and Attendance Management")
                                .contact(
                                        new Contact()
                                                .name("Adarsh Kesharwani")
                                                .email("adarsh2301k@gmail.com")
                                )
                );
    }
}