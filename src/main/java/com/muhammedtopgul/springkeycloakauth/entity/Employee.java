package com.muhammedtopgul.springkeycloakauth.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 15:27
 */
@Entity
@NoArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
}
