package com.muhammedtopgul.springkeycloakauth.repository;

import com.muhammedtopgul.springkeycloakauth.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author muhammed-topgul
 * @since 01/07/2022 15:29
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
