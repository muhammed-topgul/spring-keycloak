package com.muhammedtopgul.springkeycloakauth.service;

import com.muhammedtopgul.springkeycloakauth.entity.Employee;
import com.muhammedtopgul.springkeycloakauth.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 15:30
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @PostConstruct
    public void initializeEmployees() {
        List<Employee> employees = Stream.of(
                        new Employee("John", 20000),
                        new Employee("Mark", 55000),
                        new Employee("Peter", 120000))
                .collect(Collectors.toList());
        employeeRepository.saveAll(employees);
    }

    public Employee findById(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElse(null);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
