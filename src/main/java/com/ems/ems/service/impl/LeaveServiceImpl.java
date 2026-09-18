package com.ems.ems.service.impl;

import com.ems.ems.entity.Leave;
import com.ems.ems.repository.LeaveRepository;
import com.ems.ems.service.LeaveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository repo;

    public LeaveServiceImpl(LeaveRepository repo) {
        this.repo = repo;
    }

    //Admin
    @Override
    public Page<Leave> getAllLeaves(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Leave> getByStatus(String status, int page, int size) {
        return repo.findByStatus(status, PageRequest.of(page, size));
    }

    @Override
    public Page<Leave> searchByEmpId(Long empId, int page, int size) {
        return repo.findByEmpId(empId, PageRequest.of(page, size));
    }




    // Employee
    @Override
    public Page<Leave> getLeavesByEmpId(Long empId, int page, int size) {
        return repo.findByEmpId(empId, PageRequest.of(page, size));
    }


    @Override
    public long countLeaves() {
        return repo.count();
    }

    @Override
    public long countLeavesByEmpId(long empId) {
        return repo.countByEmpId(empId);
    }

    @Override
    public void save(Leave leave) {
        repo.save(leave);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }


}
