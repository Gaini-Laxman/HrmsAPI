package com.javafullstackguru.service;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.Payroll;
import com.javafullstackguru.repository.EmployeeRepository;
import com.javafullstackguru.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Override
    public Payroll createPayroll(Payroll payroll) {
        // Calculate gross earnings from the earnings map
        double grossEarnings = payroll.getEarnings().values().stream().mapToDouble(Double::doubleValue).sum();
        payroll.setGrossEarnings(grossEarnings);

        // Calculate total deductions from the deductions map
        double totalDeductions = payroll.getDeductions().values().stream().mapToDouble(Double::doubleValue).sum();
        payroll.setTotalDeductions(totalDeductions);

        // Calculate net pay (gross earnings - total deductions)
        double netPay = grossEarnings - totalDeductions;
        payroll.setNetPay(netPay);

        return payrollRepository.save(payroll);
    }

    @Override
    public Optional<Payroll> getPayrollById(Integer id) {
        return payrollRepository.findById(id);
    }

    @Override
    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    @Override
    public List<Payroll> getPayrollsByEmployeeId(Integer employeeId) {
        return payrollRepository.findByEmployeeEmployeeId(employeeId);
    }

    @Override
    public Payroll updatePayroll(Integer employeeId, Payroll updatedPayroll) {
        // Find the Employee by id
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Retrieve the Payroll for the Employee
        Optional<Payroll> existingPayroll = payrollRepository.findByEmployee(employee);

        if (!existingPayroll.isPresent()) {
            throw new RuntimeException("Payroll not found for employee with ID: " + employeeId);
        }

        // Update earnings and deductions
        Payroll payroll = existingPayroll.get();
        payroll.setEarnings(updatedPayroll.getEarnings());
        payroll.setDeductions(updatedPayroll.getDeductions());

        // Recalculate gross earnings, total deductions, and net pay
        double grossEarnings = payroll.getEarnings().values().stream().mapToDouble(Double::doubleValue).sum();
        payroll.setGrossEarnings(grossEarnings);

        double totalDeductions = payroll.getDeductions().values().stream().mapToDouble(Double::doubleValue).sum();
        payroll.setTotalDeductions(totalDeductions);

        double netPay = grossEarnings - totalDeductions;
        payroll.setNetPay(netPay);

        return payrollRepository.save(payroll);
    }

    @Override
    public void deletePayrollById(Integer id) {
        payrollRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    @Override
    public Optional<Payroll> getPayrollByEmployee(Integer employeeId) {
        // Fetch the employee by ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        // Return the payroll for the employee
        return payrollRepository.findByEmployee(employee);
    }
}
