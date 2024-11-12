package com.javafullstackguru.restcontroller;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + id + " does not exist");
        }
    }

    // Create new employee
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }


    // Update employee by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        if (employeeService.getEmployeeById(id) != null) {
            employee.setEmployeeId(id);
            Employee updatedEmployee = employeeService.saveOrUpdateEmployee(employee);
            String successMessage = "Employee with ID " + id + " updated successfully!";
            return ResponseEntity.ok(new UpdatedResponse(updatedEmployee, successMessage));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + id + " not found for update");
        }
    }

    // Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        if (employeeService.getEmployeeById(id) != null) {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok("Employee with ID " + id + " deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + id + " does not exist");
        }
    }

    // Response class for successful updates
    @Getter
    public static class UpdatedResponse {
        private final Employee employee;
        private final String message;

        public UpdatedResponse(Employee employee, String message) {
            this.employee = employee;
            this.message = message;
        }

    }
}
