package com.muhammedtopgul.springkeycloakauth.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 16:43
 */
@PreAuthorize("isAuthenticated()")
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {
}
