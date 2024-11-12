package com.javafullstackguru.service;

import com.javafullstackguru.entity.Attendance;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Employee saveOrUpdateEmployee(Employee employee) {
        // Fetch a managed instance if the employee already exists in the database
        if (employee.getEmployeeId() != null) {
            Employee managedEmployee = entityManager.find(Employee.class, employee.getEmployeeId());
            if (managedEmployee != null) {
                // Update fields on the managed instance to avoid detached entity issues
                managedEmployee.setName(employee.getName());
                managedEmployee.setEmail(employee.getEmail());
                managedEmployee.setPhone(employee.getPhone());
                managedEmployee.setPosition(employee.getPosition());
                managedEmployee.setDepartment(employee.getDepartment());
                managedEmployee.setDateOfJoining(employee.getDateOfJoining());

                // Ensure attendance references the managed employee
                if (employee.getAttendanceList() != null) {
                    for (Attendance attendance : employee.getAttendanceList()) {
                        attendance.setEmployee(managedEmployee);
                    }
                    managedEmployee.setAttendanceList(employee.getAttendanceList());
                }

                return entityManager.merge(managedEmployee);
            }
        }

        // For new employees or when the employee is not found
        if (employee.getAttendanceList() != null) {
            for (Attendance attendance : employee.getAttendanceList()) {
                attendance.setEmployee(employee);
            }
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

}
