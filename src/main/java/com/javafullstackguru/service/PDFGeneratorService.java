package com.javafullstackguru.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Element;  // Correct import for Element class
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.Payroll;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class   PDFGeneratorService {

    public void generatePayrollPDF(Employee employee, Payroll payroll) throws IOException, DocumentException {
        // Define the file path for the PDF
        String pdfPath = "Payslip" + employee.getEmployeeId() + ".pdf";

        // Create the PDF document object
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

        // Open the document for writing
        document.open();

        // Add title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Salary Payslip", titleFont);
        title.setAlignment(Element.ALIGN_CENTER); // Correct usage of ALIGN_CENTER
        document.add(title);

        // Add Employee Information
        Paragraph employeeInfo = new Paragraph("Employee Name: " + employee.getName() + "\n" +
                "Employee ID: " + employee.getEmployeeId() + "\n" +
                "Department: " + employee.getDepartment() + "\n\n");
        document.add(employeeInfo);

        // Add Payroll Information
        Paragraph payrollInfo = new Paragraph("Payment Date: " + payroll.getCreditDate() + "\n\n");
        document.add(payrollInfo);

        // Add Earnings Table
        document.add(new Paragraph("Earnings:"));
        PdfPTable earningsTable = new PdfPTable(2);  // PdfPTable instead of Table
        earningsTable.addCell("Earning Type");
        earningsTable.addCell("Amount");
        for (Map.Entry<String, Double> entry : payroll.getEarnings().entrySet()) {
            earningsTable.addCell(entry.getKey());
            earningsTable.addCell(String.valueOf(entry.getValue()));
        }
        document.add(earningsTable);

        // Add Deductions Table
        document.add(new Paragraph("Deductions:"));
        PdfPTable deductionsTable = new PdfPTable(2);  // PdfPTable instead of Table
        deductionsTable.addCell("Deduction Type");
        deductionsTable.addCell("Amount");
        for (Map.Entry<String, Double> entry : payroll.getDeductions().entrySet()) {
            deductionsTable.addCell(entry.getKey());
            deductionsTable.addCell(String.valueOf(entry.getValue()));
        }
        document.add(deductionsTable);

        // Add Total Salary Calculation
        document.add(new Paragraph("\nGross Earnings: " + payroll.getGrossEarnings()));
        document.add(new Paragraph("Total Deductions: " + payroll.getTotalDeductions()));
        document.add(new Paragraph("Net Pay: " + payroll.getNetPay()));

        // Close the document after adding all content
        document.close();

        // Log the PDF file location
        System.out.println("PDF generated successfully at: " + pdfPath);
    }
}
