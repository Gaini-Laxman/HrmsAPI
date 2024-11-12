package com.javafullstackguru.repository;

import com.javafullstackguru.entity.Attendance;
import com.javafullstackguru.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @EntityGraph(attributePaths = {"attendanceList"})
    // Find attendance by employee's employeeId
    List<Attendance> findByEmployeeEmployeeId(Integer employeeId);

    // Find attendance by the attendance date
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);


}
