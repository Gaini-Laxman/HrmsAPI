package com.javafullstackguru.service;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.Payroll;
import java.util.List;
import java.util.Optional;

public interface PayrollService {

    Payroll createPayroll(Payroll payroll);

    Optional<Payroll> getPayrollById(Integer id);

    List<Payroll> getAllPayrolls();

    List<Payroll> getPayrollsByEmployeeId(Integer employeeId);  // List of payrolls by employee

    Payroll updatePayroll(Integer employeeId, Payroll updatedPayroll);

    void deletePayrollById(Integer id);

    Employee getEmployeeById(Integer employeeId);  // Get Employee by ID

    // Optional: If you only need one payroll, return Optional<Payroll>
    Optional<Payroll> getPayrollByEmployee(Integer employeeId);  // Get single payroll for an employee
}
