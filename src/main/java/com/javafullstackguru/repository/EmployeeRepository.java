package com.javafullstackguru.repository;

import com.javafullstackguru.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Integer findMaxEmployeeId();
    // Custom queries can be added here if needed, but it's fine for now
}
