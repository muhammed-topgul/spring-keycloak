package com.muhammedtopgul.springkeycloakauth.service;

import com.muhammedtopgul.springkeycloakauth.config.KeycloakConfig;
import com.muhammedtopgul.springkeycloakauth.dto.UserDto;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * @author muhammed-topgul
 * @since 04/07/2022 11:19
 */
@Service
public class KeycloakAdminClientService {
    public int register(UserDto user) {
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setUsername(user.getFirstName());
        kcUser.setEmail(user.getEmail());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        Response response = usersResource.create(kcUser);
        return response.getStatus();
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
