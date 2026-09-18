package com.ems.ems.service;

import com.ems.ems.entity.Leave;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface LeaveService {

    //Admin
    Page<Leave> getAllLeaves(int page, int size);
    Page<Leave> searchByEmpId(Long empId, int page, int size);


    Page<Leave> getByStatus(String status, int page, int size);


    void save(Leave leave);
    void delete(Long id);

    //Employee
    Page<Leave> getLeavesByEmpId(Long empId, int page, int size);


    // Dashboard count
    long countLeaves();
    long countLeavesByEmpId(long empId);

}
