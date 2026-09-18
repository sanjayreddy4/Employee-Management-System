package com.ems.ems.service;

import com.ems.ems.entity.LeaveRequest;
import org.springframework.data.domain.Page;

public interface LeaveRequestService {

    void applyLeave(LeaveRequest leaveRequest);

    //  Save new leave
    LeaveRequest saveLeave(LeaveRequest leaveRequest);

    //  Get leaves by employee id (pagination)
    Page<LeaveRequest> getLeavesByEmpId(Long empId, int page, int size);

    //  Get leaves by employee id + status (pagination)
    Page<LeaveRequest> getLeavesByEmpIdAndStatus(Long empId, String status, int page, int size);

    //  Count leaves by employee id
    long countLeavesByEmpId(Long empId);

    Page<LeaveRequest> getAllLeaves(int page, int size);
    Page<LeaveRequest> getLeavesByStatus(String status, int page, int size);

    LeaveRequest getLeaveById(Long id);
    void updateStatus(Long id, String status);

    long countAllLeaves();
    long countLeavesByStatus(String status);





}
