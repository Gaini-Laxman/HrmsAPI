package com.javafullstackguru.restcontroller;

import com.javafullstackguru.entity.Attendance;
import com.javafullstackguru.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Create new attendance record for a specific employee
    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<Attendance> createAttendanceForEmployee(@PathVariable Integer employeeId) {
        Attendance createdAttendance = attendanceService.createAttendanceForEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance); // 201 Created
    }

    // Get attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Integer id) {
        return attendanceService.getAttendanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all attendances
    @GetMapping
    public List<Attendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    // Get attendance by employee ID
    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceByEmployeeId(@PathVariable Integer employeeId) {
        return attendanceService.getAttendanceByEmployeeId(employeeId);
    }

    // Update an existing attendance record
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Integer id, @RequestBody Attendance attendance) {
        Attendance updatedAttendance = attendanceService.updateAttendance(id, attendance);
        if (updatedAttendance != null) {
            return ResponseEntity.ok(updatedAttendance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if not found
        }
    }
}
