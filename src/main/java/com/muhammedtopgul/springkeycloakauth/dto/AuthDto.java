package com.muhammedtopgul.springkeycloakauth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author muhammed-topgul
 * @since 05/07/2022 15:00
 */
@Getter
@Setter
public class AuthDto {
    private String fullName;
    private String username;
    private String email;
}
