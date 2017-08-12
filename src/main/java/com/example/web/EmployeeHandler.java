package com.example.web;

import com.example.domain.Employee;
import com.example.domain.EmployeeByLastName;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class EmployeeHandler {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeHandler(EmployeeService hotelService) {
        this.employeeService = hotelService;
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        UUID uuid = UUID.fromString(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return this.employeeService.findOne(uuid)
                .flatMap(employee -> ServerResponse.ok().body(Mono.just(employee), Employee.class))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<Employee> employeeToBeCreated = serverRequest.bodyToMono(Employee.class);
        return employeeToBeCreated.flatMap(employee ->
                ServerResponse.status(HttpStatus.CREATED).body(employeeService.save(employee), Employee.class));
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        Mono<Employee> employeeToBeUpdated = serverRequest.bodyToMono(Employee.class);

        return employeeToBeUpdated.flatMap(employee ->
                ServerResponse.status(HttpStatus.CREATED).body(employeeService.update(employee), Employee.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        UUID uuid = UUID.fromString(serverRequest.pathVariable("id"));

        return this.employeeService.delete(uuid).flatMap(result -> ServerResponse.accepted().build());
    }

    public Mono<ServerResponse> findEmployeesWithLastName(ServerRequest serverRequest) {
        String lastName = serverRequest.pathVariable("lastName");

        Flux<EmployeeByLastName> employeesWithLastName = this.employeeService.findEmployeesWithLastName(lastName);
        return ServerResponse.ok().body(employeesWithLastName, EmployeeByLastName.class);
    }

}
