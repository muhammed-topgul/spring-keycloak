package com.muhammedtopgul.springkeycloakauth.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 16:43
 */
@PreAuthorize("hasRole('Admin')")
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Admin {
}
