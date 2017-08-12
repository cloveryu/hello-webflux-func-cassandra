package com.example.repository;

import com.example.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class EmployeeRepository {

    private final ReactiveCassandraOperations cassandraTemplate;

    @Autowired
    public EmployeeRepository(ReactiveCassandraOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    public Mono<Employee> save(Employee employee) {
        return cassandraTemplate.insert(employee);
    }

    public Mono<Employee> update(Employee employee) {
        return cassandraTemplate.update(employee);
    }

    public Mono<Employee> findOne(UUID employeeId) {
        return cassandraTemplate.selectOneById(employeeId, Employee.class);
    }

    public Mono<Boolean> delete(UUID employeeId) {
        return cassandraTemplate.deleteById(employeeId, Employee.class);
    }

}
