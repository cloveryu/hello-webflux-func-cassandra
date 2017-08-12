package com.example.controller;

import com.example.domain.Employee;
import com.example.domain.EmployeeByLastName;
import com.example.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class EmployeeControllerTest {

    private WebTestClient client;

    private EmployeeService employeeService;

    private UUID sampleUUID = UUID.fromString("d365bbf3-ceef-4566-b154-153907264c7c");
    private Employee employee;

    @Before
    public void setUp() {
        this.employeeService = mock(EmployeeService.class);
        employee = Employee.builder()
                .id(sampleUUID)
                .firstName("Yang")
                .lastName("YU")
                .build();
        when(employeeService.findOne(sampleUUID)).thenReturn(Mono.just(employee));
        when(employeeService.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee employee = invocation.getArgument(0);
            return Mono.just(employee);
        });
        when(employeeService.update(any(Employee.class))).thenAnswer(invocation -> {
            Employee employee = invocation.getArgument(0);
            return Mono.just(employee);
        });
        when(employeeService.findEmployeesWithLastName("YU"))
                .thenReturn(Flux.just(new EmployeeByLastName(employee)));

        this.client = WebTestClient.bindToController(new EmployeeController(employeeService)).build();
    }

    @Test
    public void shouldGetEmployee() throws Exception {
        this.client.get().uri("/employees/" + sampleUUID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class)
                .isEqualTo(employee);
    }

    @Test
    public void shouldSaveEmployee() throws Exception {
        this.client.post().uri("/employees")
                .body(fromObject(employee))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Employee.class)
                .isEqualTo(employee);

        verify(this.employeeService).save(employee);
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        this.client.put().uri("/employees")
                .body(fromObject(employee))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Employee.class)
                .isEqualTo(employee);
    }

    @Test
    public void shouldGetEmployeesWithLastName() {
        this.client.get().uri("/employees/withlastname/YU")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeByLastName.class)
                .hasSize(1);
    }

}