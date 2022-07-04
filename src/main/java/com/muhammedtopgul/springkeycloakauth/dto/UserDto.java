package com.muhammedtopgul.springkeycloakauth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author muhammed-topgul
 * @since 04/07/2022 11:18
 */
@Getter
@Setter
public class UserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
