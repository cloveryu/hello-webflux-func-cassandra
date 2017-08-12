package com.example.web;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public interface ApplicationRoutes {
    static RouterFunction<?> routes(EmployeeHandler employeeHandler) {
        return nest(path("/employees"),
                nest(accept(MediaType.APPLICATION_JSON),
                        route(GET("/{id}"), employeeHandler::get)
                                .andRoute(POST("/"), employeeHandler::save)
                                .andRoute(PUT("/"), employeeHandler::update)
                                .andRoute(DELETE("/{id}"), employeeHandler::delete)
                                .andRoute(GET("/withlastname/{lastName}"), employeeHandler::findEmployeesWithLastName)
                ));
    }
    
}
