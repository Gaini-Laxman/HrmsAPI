package com.javafullstackguru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    @JsonBackReference // Indicates the child side of the reference
    private Employee employee;

    @ElementCollection
    @CollectionTable(name = "payroll_earnings", joinColumns = @JoinColumn(name = "payroll_id"))
    @MapKeyColumn(name = "earning_type")
    @Column(name = "amount")
    private Map<String, Double> earnings; // Example: {"Basic": 41035.00, "HRA": 20518.00}

    @ElementCollection
    @CollectionTable(name = "payroll_earnings_ytd", joinColumns = @JoinColumn(name = "payroll_id"))
    @MapKeyColumn(name = "earning_type")
    @Column(name = "year_to_date")
    private Map<String, Double> earningsYTD; // Example: {"Basic": 186480.00, "HRA": 93242.00}

    @ElementCollection
    @CollectionTable(name = "payroll_deductions", joinColumns = @JoinColumn(name = "payroll_id"))
    @MapKeyColumn(name = "deduction_type")
    @Column(name = "amount")
    private Map<String, Double> deductions; // Example: {"EPF": 1800.00, "Professional Tax": 500.00}

    @ElementCollection
    @CollectionTable(name = "payroll_deductions_ytd", joinColumns = @JoinColumn(name = "payroll_id"))
    @MapKeyColumn(name = "deduction_type")
    @Column(name = "year_to_date")
    private Map<String, Double> deductionsYTD; // Example: {"EPF": 7200.00, "Professional Tax": 1250.00}

    @Column(nullable = false)
    private Double grossEarnings; // Sum of earnings

    @Column(nullable = false)
    private Double totalDeductions; // Sum of deductions

    @Column(nullable = false)
    private Double netPay; // Gross earnings - Total deductions

    @Column(nullable = false)
    private LocalDate creditDate;
}
