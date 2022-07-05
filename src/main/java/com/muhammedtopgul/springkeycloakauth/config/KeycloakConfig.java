package com.muhammedtopgul.springkeycloakauth.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author muhammed-topgul
 * @since 04/07/2022 11:14
 */
@Component
public class KeycloakConfig {
    static Keycloak keycloak = null;
    public final static String serverUrl = "http://localhost:8180/auth";
    public final static String realm = "medium-test";
    public final static String clientId = "medium-test-frontend-client";
    public final static String clientSecret = "VuJ2LzksMZmVr8TfODu7vTaYEM3ozXnd";
    public final static String userName = "medium-test-admin";
    public final static String password = "12345";
    public final static String client = "medium-test-frontend-client";

    public KeycloakConfig() {
    }

    public static Keycloak getInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder()
                            .connectionPoolSize(10)
                            .build())
                    .build();
        }
        return keycloak;
    }

    @Bean
    public AuthzClient getAuthZClient() {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put(OAuth2Constants.GRANT_TYPE, OAuth2Constants.PASSWORD);

        Configuration configuration = new Configuration(serverUrl, realm, client, clientCredentials, null);
        return AuthzClient.create(configuration);
    }
}
