package com.javafullstackguru.repository;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
    List<Payroll> findByEmployeeEmployeeId(Integer employeeId);

    Optional<Payroll> findByEmployee(Employee employee);
}
