package com.muhammedtopgul.springkeycloakauth.service;

import com.muhammedtopgul.springkeycloakauth.config.KeycloakConfig;
import com.muhammedtopgul.springkeycloakauth.dto.UserDto;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.muhammedtopgul.springkeycloakauth.config.KeycloakConfig.realm;

/**
 * @author muhammed-topgul
 * @since 04/07/2022 11:19
 */
@Service
public class KeycloakAdminClientService {
    public int register(UserDto user) {
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        userRepresentation.setUsername(user.getFirstName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);
        Response response = usersResource.create(userRepresentation);

        setRoleToUser(user.getFirstName(), "Admin");

        return response.getStatus();
    }

    private void setRoleToUser(String username, String role) {
        String userId = KeycloakConfig.getInstance()
                .realm(realm)
                .users()
                .search(username)
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
                .get(role)
                .toRepresentation()
        );
        user.roles().realmLevel().add(roleToAdd);
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
