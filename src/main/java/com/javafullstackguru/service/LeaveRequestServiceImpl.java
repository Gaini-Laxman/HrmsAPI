package com.javafullstackguru.service;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.entity.LeaveRequest;
import com.javafullstackguru.repository.EmployeeRepository;
import com.javafullstackguru.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        Integer employeeId = leaveRequest.getEmployee().getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        leaveRequest.setEmployee(employee);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public Optional<LeaveRequest> getLeaveRequestById(Integer id) {
        return leaveRequestRepository.findById(id);
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Integer employeeId) {
        // Custom query or logic to find leave requests by employeeId if needed
        return leaveRequestRepository.findAll().stream()
                .filter(lr -> lr.getEmployee().getEmployeeId().equals(employeeId))
                .toList();
    }

    @Override
    public LeaveRequest updateLeaveRequest(Integer id, LeaveRequest updatedLeaveRequest) {
        return leaveRequestRepository.findById(id).map(leaveRequest -> {
            leaveRequest.setLeaveType(updatedLeaveRequest.getLeaveType());
            leaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
            leaveRequest.setEndDate(updatedLeaveRequest.getEndDate());
            leaveRequest.setStatus(updatedLeaveRequest.getStatus());
            return leaveRequestRepository.save(leaveRequest);
        }).orElse(null);
    }

    @Override
    public void deleteLeaveRequestById(Integer id) {
        leaveRequestRepository.deleteById(id);
    }
}
