package com.example.repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.example.domain.EmployeeByLastName;
import com.example.domain.EmployeeByLastNameKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeByLastNameRepository {

    private final ReactiveCassandraOperations cassandraTemplate;

    @Autowired
    public EmployeeByLastNameRepository(ReactiveCassandraOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    public Flux<EmployeeByLastName> findByLastName(String lastName) {
        Select select = QueryBuilder.select().from("employees_by_last_name");
        select.where(QueryBuilder.eq("last_name", lastName));
        return cassandraTemplate.select(select, EmployeeByLastName.class);
    }

    public Mono<EmployeeByLastName> save(EmployeeByLastName employeeByLastName) {
        return cassandraTemplate.insert(employeeByLastName);
    }

    public Mono<Boolean> delete(EmployeeByLastNameKey employeeByLastNameKey) {
        return cassandraTemplate.deleteById(employeeByLastNameKey, EmployeeByLastName.class);
    }

}
