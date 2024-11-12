package com.javafullstackguru.restcontroller;

import com.itextpdf.text.DocumentException;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.Payroll;
import com.javafullstackguru.service.PDFGeneratorService;
import com.javafullstackguru.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @PostMapping
    public ResponseEntity<Payroll> createPayroll(@RequestBody Payroll payroll) {
        Payroll createdPayroll = payrollService.createPayroll(payroll);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayroll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payroll> getPayrollById(@PathVariable Integer id) {
        return payrollService.getPayrollById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Payroll> getAllPayrolls() {
        return payrollService.getAllPayrolls();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Payroll> getPayrollsByEmployeeId(@PathVariable Integer employeeId) {
        return payrollService.getPayrollsByEmployeeId(employeeId);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable Integer employeeId, @RequestBody Payroll updatedPayroll) {
        Payroll payroll = payrollService.updatePayroll(employeeId, updatedPayroll);
        return ResponseEntity.ok(payroll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayrollById(@PathVariable Integer id) {
        payrollService.deletePayrollById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to generate payroll PDF for a specific employee by ID
    @GetMapping("/generate-pdf/{employeeId}")
    public ResponseEntity<String> generatePayrollPDF(@PathVariable Integer employeeId) {
        try {
            // Fetch the employee and corresponding payroll
            Employee employee = payrollService.getEmployeeById(employeeId);
            Optional<Payroll> payrollOpt = payrollService.getPayrollByEmployee(employeeId);

            if (payrollOpt.isPresent()) {
                Payroll payroll = payrollOpt.get();

                // Call the method to generate PDF
                pdfGeneratorService.generatePayrollPDF(employee, payroll);

                return ResponseEntity.ok("PDF generated successfully for Employee ID: " + employeeId);
            } else {
                // No payroll found for the employee
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Payroll not found for Employee ID: " + employeeId);
            }
        } catch (IOException | DocumentException e) {
            // Handle any errors during PDF generation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating PDF: " + e.getMessage());
        }
    }

}
