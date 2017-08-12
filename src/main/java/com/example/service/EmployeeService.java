package com.example.service;

import com.example.domain.Employee;
import com.example.domain.EmployeeByLastName;
import com.example.repository.EmployeeByLastNameRepository;
import com.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeByLastNameRepository employeeByLastNameRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeByLastNameRepository employeeByLastNameRepository) {

        this.employeeRepository = employeeRepository;
        this.employeeByLastNameRepository = employeeByLastNameRepository;
    }

    public Mono<Employee> save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(UUID.randomUUID());
        }
        return employeeByLastNameRepository.save(new EmployeeByLastName(employee))
                .then(employeeRepository.save(employee));
    }

    public Mono<Employee> update(Employee employee) {
        return employeeRepository.findOne(employee.getId())
                .flatMap(existingEmployee ->
                        employeeByLastNameRepository.delete(new EmployeeByLastName(existingEmployee).getEmployeeByLastNameKey())
                                .then(employeeByLastNameRepository.save(new EmployeeByLastName(employee)))
                                .then(employeeRepository.update(employee)));
    }

    public Mono<Employee> findOne(UUID uuid) {
        return employeeRepository.findOne(uuid);
    }

    public Mono<Boolean> delete(UUID uuid) {
        Mono<Employee> employeeMono = employeeRepository.findOne(uuid);
        return employeeMono
                .flatMap((Employee employee) -> employeeRepository.delete(employee.getId())
                        .then(employeeByLastNameRepository
                                .delete(new EmployeeByLastName(employee).getEmployeeByLastNameKey())));
    }

    public Flux<EmployeeByLastName> findEmployeesWithLastName(String lastName) {
        return employeeByLastNameRepository.findByLastName(lastName);
    }

}
