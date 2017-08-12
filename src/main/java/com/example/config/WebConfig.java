package com.example.config;

import com.example.web.ApplicationRoutes;
import com.example.web.EmployeeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
public class WebConfig {

    @Autowired
    private EmployeeHandler employeeHandler;

    @Bean
    public RouterFunction<?> routerFunction() {
        return ApplicationRoutes.routes(employeeHandler);
    }
}
