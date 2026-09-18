package com.ems.ems.service.impl;

import com.ems.ems.entity.LeaveRequest;
import com.ems.ems.repository.LeaveRequestRepository;
import com.ems.ems.service.LeaveRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository repo;

    public LeaveRequestServiceImpl(LeaveRequestRepository repo) {
        this.repo = repo;
    }

    @Override
    public void applyLeave(LeaveRequest leaveRequest) {
        repo.save(leaveRequest);
    }

    // Save
    @Override
    public LeaveRequest saveLeave(LeaveRequest leaveRequest) {
        return repo.save(leaveRequest);
    }


    //Employee leaves by empId
    @Override
    public Page<LeaveRequest> getLeavesByEmpId(Long empId, int page, int size) {
        return repo.findByEmployee_Id(empId, PageRequest.of(page, size));
    }

    @Override
    public long countLeavesByEmpId(Long empId) {
        return repo.countByEmployee_Id(empId);
    }

    // Admin: get all leaves
    @Override
    public Page<LeaveRequest> getAllLeaves(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    // Admin: filter all leaves by status
    @Override
    public Page<LeaveRequest> getLeavesByStatus(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findByStatus(status, pageable);
    }


    //Employee leaves by empId + status
    @Override
    public Page<LeaveRequest> getLeavesByEmpIdAndStatus(Long empId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findByEmployee_IdAndStatus(empId, status, pageable);
    }

    @Override
    public LeaveRequest getLeaveById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
    }

    @Override
    public void updateStatus(Long id, String status) {
        LeaveRequest leave = getLeaveById(id);
        leave.setStatus(status);
        repo.save(leave);
    }

    @Override
    public long countAllLeaves() {
        return repo.count();
    }

    @Override
    public long countLeavesByStatus(String status) {
        return repo.countByStatus(status);
    }




}
