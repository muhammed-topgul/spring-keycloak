package com.muhammedtopgul.springkeycloakauth.controller;

import com.muhammedtopgul.springkeycloakauth.annotation.Admin;
import com.muhammedtopgul.springkeycloakauth.config.KeycloakConfig;
import com.muhammedtopgul.springkeycloakauth.dto.AuthDto;
import com.muhammedtopgul.springkeycloakauth.dto.UserDto;
import com.muhammedtopgul.springkeycloakauth.entity.Employee;
import com.muhammedtopgul.springkeycloakauth.service.EmployeeService;
import com.muhammedtopgul.springkeycloakauth.service.KeycloakAdminClientService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.muhammedtopgul.springkeycloakauth.config.KeycloakConfig.realm;

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
    public ResponseEntity<Employee> findById(@PathVariable long employeeId, Principal principal) {
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }

    @GetMapping("/userDetails")
    public ResponseEntity<AuthDto> getUserDetails(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();

        AuthDto userDto = new AuthDto();
        userDto.setUsername(accessToken.getPreferredUsername());
        userDto.setEmail(accessToken.getEmail());
        userDto.setFullName(accessToken.getName());
        return ResponseEntity.ok(userDto);
    }

    //    @RolesAllowed({Permission.ADMIN, Permission.USER})
    @Admin
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

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        String url = KeycloakConfig.serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        String clientId = KeycloakConfig.clientId;
        String secret = KeycloakConfig.clientSecret;
        Http http = new Http(keycloakConfig.getAuthZClient().getConfiguration(), (params, headers) -> {
        });

        AccessTokenResponse response = http.<AccessTokenResponse>post(url)
                .authentication()
                .client()
                .form()
                .param("grant_type", "refresh_token")
                .param("refresh_token", refreshToken)
                .param("client_id", clientId)
                .param("client_secret", secret)
                .response()
                .json(AccessTokenResponse.class)
                .execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello!";
    }


    @GetMapping("/set-role/{userName}")
    public void setRole(@PathVariable String userName) {
        System.out.println(getAllRoles());
        String userId = KeycloakConfig.getInstance()
                .realm(realm)
                .users()
                .search(userName)
                .get(0)
                .getId();

        UserResource user = KeycloakConfig.getInstance()
                .realm(realm)
                .users()
                .get(userId);

        List<RoleRepresentation> roleToAdd = new LinkedList<>();
        roleToAdd.add(KeycloakConfig.getInstance()
                .realm(realm)
                .roles()
                .get("Admin")
                .toRepresentation()
        );
        user.roles().realmLevel().add(roleToAdd);
    }

    @GetMapping("/remove-role/{userName}")
    public void removeRole(@PathVariable String userName) {
        System.out.println(getAllRoles());
        String userId = KeycloakConfig.getInstance()
                .realm(realm)
                .users()
                .search(userName)
                .get(0)
                .getId();

        UserResource user = KeycloakConfig.getInstance()
                .realm(realm)
                .users()
                .get(userId);

        List<RoleRepresentation> roleToAdd = new LinkedList<>();
        roleToAdd.add(KeycloakConfig.getInstance()
                .realm(realm)
                .roles()
                .get("Admin")
                .toRepresentation()
        );
        user.roles().realmLevel().remove(roleToAdd);
    }

    public List<String> getAllRoles() {
        return KeycloakConfig.getInstance()
                .realm(realm)
                .roles()
                .list()
                .stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
    }
}
