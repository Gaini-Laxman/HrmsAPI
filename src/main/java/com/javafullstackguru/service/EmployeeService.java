package com.javafullstackguru.service;

import com.javafullstackguru.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(Integer id);
    //Employee updateEmployee(Employee employee);
    void deleteEmployeeById(Integer id); // Fixed method name
    Employee saveOrUpdateEmployee(Employee employee);
}
