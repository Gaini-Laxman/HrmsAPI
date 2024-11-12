package com.javafullstackguru.service;

import com.javafullstackguru.entity.Attendance;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    Attendance createAttendanceForEmployee(Integer employeeId); // Changed parameter type
    Optional<Attendance> getAttendanceById(Integer id);
    List<Attendance> getAllAttendances();
    List<Attendance> getAttendanceByEmployeeId(Integer employeeId);
    Attendance updateAttendance(Integer id, Attendance updatedAttendance);
}
