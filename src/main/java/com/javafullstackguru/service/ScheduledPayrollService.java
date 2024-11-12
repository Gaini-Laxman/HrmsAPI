package com.javafullstackguru.service;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.Payroll;
import com.javafullstackguru.repository.EmployeeRepository;
import com.javafullstackguru.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduledPayrollService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    // This method runs at 7 AM on the 1st of every month
    @Scheduled(cron = "0 0 7 1 * ?")
    public void generateMonthlyPayroll() {
        System.out.println("Generating monthly payroll...");

        // Fetch all employees from the repository
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            // Calculate earnings and deductions using the new structure
            double baseSalary = employee.getSalary(); // Assume `getSalary()` returns the base salary

            // Earnings map with sample values
            Map<String, Double> earnings = new HashMap<>();
            earnings.put("Basic", baseSalary);
            earnings.put("HRA", calculateHRA(baseSalary));  // Example: HRA is 20% of base salary
            earnings.put("Bonus", calculateBonuses(employee)); // Sample bonus calculation

            // Deductions map with sample values
            Map<String, Double> deductions = new HashMap<>();
            deductions.put("EPF", calculateEPF(baseSalary)); // Sample EPF deduction
            deductions.put("Tax", calculateTax(baseSalary)); // Sample tax calculation

            // Calculate gross earnings and total deductions
            double grossEarnings = earnings.values().stream().mapToDouble(Double::doubleValue).sum();
            double totalDeductions = deductions.values().stream().mapToDouble(Double::doubleValue).sum();
            double netPay = grossEarnings - totalDeductions;

            // Create a new Payroll record
            Payroll payroll = new Payroll();
            payroll.setEmployee(employee);
            payroll.setEarnings(earnings);
            payroll.setDeductions(deductions);
            payroll.setGrossEarnings(grossEarnings);
            payroll.setTotalDeductions(totalDeductions);
            payroll.setNetPay(netPay);
            payroll.setCreditDate(LocalDate.now());  // Set the current date as the credit date

            // Save the payroll record to the database
            payrollRepository.save(payroll);
            System.out.println("Payroll processed for employee: " + employee.getName());
        }

        System.out.println("Monthly payroll generation complete.");
    }

    private double calculateHRA(double baseSalary) {
        // Sample logic for HRA calculation
        return baseSalary * 0.2; // HRA is 20% of base salary
    }

    private double calculateBonuses(Employee employee) {
        // Placeholder logic for calculating bonuses
        return 500; // Sample bonus value
    }

    private double calculateEPF(double baseSalary) {
        // Placeholder logic for calculating EPF deduction
        return baseSalary * 0.12; // EPF is 12% of base salary
    }

    private double calculateTax(double baseSalary) {
        // Placeholder logic for tax deduction
        return baseSalary * 0.1; // Tax is 10% of base salary
    }
}
