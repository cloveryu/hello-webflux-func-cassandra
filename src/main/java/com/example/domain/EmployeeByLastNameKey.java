package com.example.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Data
@Builder
@PrimaryKeyClass
public class EmployeeByLastNameKey {

    @PrimaryKeyColumn(name="last_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String lastName;

    @PrimaryKeyColumn(name="employee_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID employeeId;

}
