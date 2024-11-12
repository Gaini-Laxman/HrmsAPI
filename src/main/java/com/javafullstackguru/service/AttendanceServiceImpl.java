package com.javafullstackguru.service;

import com.javafullstackguru.entity.Attendance;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.repository.AttendanceRepository;
import com.javafullstackguru.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Attendance createAttendanceForEmployee(Integer employeeId) {
        // Fetch the managed Employee instance from the database
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create a new Attendance instance and set its fields
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setCheckInTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 30)));
        attendance.setCheckoutTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 30)));

        attendance.setStatus("Present");

        // Set the managed employee instance to the attendance
        attendance.setEmployee(employee);

        // Save the attendance
        return attendanceRepository.save(attendance);
    }

    @Override
    public Optional<Attendance> getAttendanceById(Integer id) {
        return attendanceRepository.findById(id);
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<Attendance> getAttendanceByEmployeeId(Integer employeeId) {
        return attendanceRepository.findByEmployeeEmployeeId(employeeId);
    }

    @Override
    public Attendance updateAttendance(Integer id, Attendance updatedAttendance) {
        return attendanceRepository.findById(id).map(attendance -> {
            attendance.setAttendanceDate(updatedAttendance.getAttendanceDate());
            attendance.setCheckInTime(updatedAttendance.getCheckInTime());
            attendance.setCheckoutTime(updatedAttendance.getCheckoutTime());
            attendance.setStatus(updatedAttendance.getStatus());

            // Ensure the employee reference remains consistent
            attendance.setEmployee(updatedAttendance.getEmployee());

            return attendanceRepository.save(attendance);
        }).orElse(null);
    }
}
