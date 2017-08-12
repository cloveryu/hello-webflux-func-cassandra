package com.example.controller;

import com.example.domain.Employee;
import com.example.domain.EmployeeByLastName;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{id}")
    public Mono<Employee> get(@PathVariable("id") UUID uuid) {
        return employeeService.findOne(uuid);
    }

    @PostMapping
    public Mono<ResponseEntity<Employee>> save(@RequestBody Employee employee) {
        return employeeService.save(employee)
                .map(savedEmployee -> new ResponseEntity<>(savedEmployee, HttpStatus.CREATED));
    }

    @PutMapping
    public Mono<ResponseEntity<Employee>> update(@RequestBody Employee employee) {
        return employeeService.update(employee)
                .map(savedEmployee -> new ResponseEntity<>(savedEmployee, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable("id") UUID uuid) {
        return employeeService.delete(uuid).map((Boolean status) ->
                new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED));
    }

    @GetMapping(path = "/withlastname/{lastName}")
    public Flux<EmployeeByLastName> findEmployeesWithLastName(
            @PathVariable("lastName") String lastName) {
        return employeeService.findEmployeesWithLastName(lastName);
    }

}
