package com.muhammedtopgul.springkeycloakauth.controller;

import com.muhammedtopgul.springkeycloakauth.annotation.Admin;
import com.muhammedtopgul.springkeycloakauth.annotation.User;
import com.muhammedtopgul.springkeycloakauth.entity.Employee;
import com.muhammedtopgul.springkeycloakauth.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 15:35
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @User
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findById(@PathVariable long employeeId) {
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }

    @Admin
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }
}
