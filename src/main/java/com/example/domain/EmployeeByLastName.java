package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("employees_by_last_name")
public class EmployeeByLastName {

    @PrimaryKey
    private EmployeeByLastNameKey employeeByLastNameKey;

    private String firstName;
    private String gender;
    private int age;
    private String birthDate;

    public EmployeeByLastName(Employee employee) {
        this.employeeByLastNameKey = EmployeeByLastNameKey.builder()
                .lastName(employee.getLastName())
                .employeeId(employee.getId())
                .build();
        this.firstName = employee.getFirstName();
        this.gender = employee.getGender();
        this.age = employee.getAge();
        this.birthDate = employee.getBirthDate();
    }
}
