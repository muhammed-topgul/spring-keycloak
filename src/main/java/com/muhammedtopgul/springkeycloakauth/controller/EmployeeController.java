package com.muhammedtopgul.springkeycloakauth.controller;

import com.muhammedtopgul.springkeycloakauth.annotation.Admin;
import com.muhammedtopgul.springkeycloakauth.config.KeycloakConfig;
import com.muhammedtopgul.springkeycloakauth.dto.UserDto;
import com.muhammedtopgul.springkeycloakauth.entity.Employee;
import com.muhammedtopgul.springkeycloakauth.service.EmployeeService;
import com.muhammedtopgul.springkeycloakauth.service.KeycloakAdminClientService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 15:35
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final KeycloakAdminClientService kcAdminClient;
    private final KeycloakConfig keycloakConfig;

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findById(@PathVariable long employeeId) {
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }

    @Admin
//    @RolesAllowed({Permission.ADMIN, Permission.USER})
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Admin
    @PostMapping(value = "/register")
    public int createUser(@RequestBody UserDto user) {
        return kcAdminClient.register(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody UserDto user) {
        AccessTokenResponse accessTokenResponse = keycloakConfig.getAuthZClient().obtainAccessToken(user.getFirstName(), user.getPassword());
        return ResponseEntity.ok(accessTokenResponse);
    }

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello!";
    }
}
