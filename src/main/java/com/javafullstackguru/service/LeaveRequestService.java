package com.javafullstackguru.service;

import com.javafullstackguru.entity.LeaveRequest;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestService {
    LeaveRequest createLeaveRequest(LeaveRequest leaveRequest);
    Optional<LeaveRequest> getLeaveRequestById(Integer id);
    List<LeaveRequest> getAllLeaveRequests();
    List<LeaveRequest> getLeaveRequestsByEmployeeId(Integer employeeId);
    LeaveRequest updateLeaveRequest(Integer id, LeaveRequest updatedLeaveRequest);
    void deleteLeaveRequestById(Integer id);
}
